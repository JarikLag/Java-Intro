package crawler;

import base.Pair;

import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;
import java.util.regex.*;

public class SimpleWebCrawler implements WebCrawler {
    private Downloader downloader;

    public SimpleWebCrawler(Downloader downloader) throws IOException {
        this.downloader = downloader;
    }

    private static List<String> extractDoubleTag(String blank, String tagName) {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("<" + tagName + "[\\s\\S]*" + "?</" + tagName + ">");
        Matcher matcher = pattern.matcher(blank);
        while (matcher.find()) {
            list.add(blank.substring(matcher.start(), matcher.end()));
        }
        return list;
    }

    private static List<String> extractTag(String blank, String tagName) {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("<" + tagName + "[\\s\\S]*?>");
        Matcher matcher = pattern.matcher(blank);
        while (matcher.find()) {
            list.add(blank.substring(matcher.start(), matcher.end()));
        }
        return list;
    }

    private static String extractTitle(String blank) {
        Pattern pattern = Pattern.compile("<title>" + "([\\s\\S]*)" + "</title>");
        Matcher matcher = pattern.matcher(blank);
        return matcher.find() ? matcher.group(1) : "";
    }

    private static String deleteComments(String page) {
        return page.replaceAll("<!--[\\s\\S]*?-->", "");
    }

    private static String deleteAnchor(String url) {
        int pos = url.length();
        for (int i = 0; i < url.length(); i++) {
            if (url.charAt(i) == '#') {
                pos = i;
                break;
            }
        }
        return url.substring(0, pos);
    }

    private static String extractAttribute(String tag, String attribute) {
        Pattern pattern = Pattern.compile(attribute + "\\s*=\\s*\"([\\s\\S]*?)\"");
        Matcher matcher = pattern.matcher(tag);
        return matcher.find() ? matcher.group(1) : null;
    }

    private static String generateLocalFilename(String url) {
        return url.replaceAll("://", "_").replaceAll("/", "_");
    }

    private static String replaceEntities(String str) {
        return str.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&mdash;", "\u2014").replaceAll("&nbsp;", "\u00A0");
    }

    @Override
    public Page crawl(String url, int depth) {
        Map<String, Page> pages = new HashMap<>();
        Map<String, Image> images = new HashMap<>();
        Map<String, List<String>> childLinks = new LinkedHashMap<>();
        Deque<Pair<String, Integer>> deque = new ArrayDeque<>();
        List<Page> connections = new ArrayList<>();
        URL rootPage;
        try {
            rootPage = new URL(url);
        } catch (MalformedURLException e) {
            System.out.println(url + " is not a valid URL");
            return null;
        }

        deque.add(Pair.of(rootPage.toString(), depth));

        while (!deque.isEmpty()) {
            List<Image> localImages = new ArrayList<>();
            String localUrl = deque.getFirst().first;
            int localDepth = deque.getFirst().second;
            deque.removeFirst();
            if (pages.containsKey(localUrl)) {
                continue;
            } else if (localDepth == 0) {
                pages.put(localUrl, new Page(localUrl, ""));
            } else {
                String pageContent;
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(downloader.download(localUrl), "utf8"))) {
                    StringBuilder text = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null)
                        text.append(inputLine);
                    pageContent = text.toString();
                } catch (IOException e) {
                    System.out.println("Page read failed: " + e.getMessage());
                    pages.put(localUrl, new Page(localUrl, ""));
                    continue;
                }
                URL convertedLocalURL;
                try {
                    convertedLocalURL = new URL(localUrl);
                } catch (MalformedURLException e) {
                    System.out.println(url + " is not a valid URL");
                    continue;
                }

                String title = replaceEntities(extractTitle(pageContent));
                Page localPage = new Page(localUrl, title);
                pageContent = deleteComments(pageContent);
                List<String> tagsWithLinks = extractTag(pageContent, "a");

                List<String> tmp = new ArrayList<>();
                for (int i = 0; i < tagsWithLinks.size(); i++) {
                    String possibleLink = extractAttribute(tagsWithLinks.get(i), "href");
                    if (possibleLink == null || possibleLink.equals("")) {
                        continue;
                    }
                    possibleLink = replaceEntities(deleteAnchor(possibleLink));
                    URL childUrl;
                    try {
                        childUrl = new URL(convertedLocalURL, possibleLink);
                    } catch (MalformedURLException e) {
                        System.out.println(url + " is not a valid URL");
                        continue;
                    }
                    deque.add(Pair.of(childUrl.toString(), localDepth - 1)); //check

                    tmp.add(childUrl.toString());
                }
                childLinks.put(localUrl, tmp);

                List<String> tagsWithImages = extractTag(pageContent, "img");

                for (int i = 0; i < tagsWithImages.size(); i++) {
                    String rawImageLink = extractAttribute(tagsWithImages.get(i), "src");
                    if (rawImageLink == null || rawImageLink.equals("")) {
                        continue;
                    }
                    rawImageLink = replaceEntities(rawImageLink);
                    URL imageURL;
                    try {
                        imageURL = new URL(convertedLocalURL, rawImageLink);
                    } catch (MalformedURLException e) {
                        System.out.println(url + " is not a valid URL");
                        continue;
                    }
                    if (!images.containsKey(imageURL.toString())) {
                        try {
                            ReadableByteChannel rbc = Channels.newChannel(downloader.download(imageURL.toString()));
                            FileOutputStream fos = new FileOutputStream(generateLocalFilename(imageURL.toString()));
                            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                            Image img = new Image(imageURL.toString(), generateLocalFilename(imageURL.toString()));
                            localImages.add(img);
                            images.put(imageURL.toString(), img);
                        } catch (FileNotFoundException e) {
                            System.out.println("Could not create " + generateLocalFilename(imageURL.toString()));
                        } catch (IOException e) {
                            System.out.println("Could not download " + imageURL);
                        }
                    } else {
                        localImages.add(images.get(imageURL.toString()));
                    }
                }
                for (int i = 0; i < localImages.size(); i++) {
                    localPage.addImage(localImages.get(i));
                }
                pages.put(localUrl, localPage);
                connections.add(localPage);
            }
        }
        for (int i = connections.size() - 1; i >= 0; i--) {
            Page currentPage = connections.get(i);
            List<String> now = childLinks.get(currentPage.getUrl());
            for (int j = 0; j < now.size(); j++) {
                currentPage.addLink(pages.get(now.get(j)));
            }
            pages.put(currentPage.getUrl(), currentPage);
        }
        return pages.get(rootPage.toString());
    }
}
