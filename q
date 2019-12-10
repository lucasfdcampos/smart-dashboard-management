[33mcommit 26f6da7b85850738019cf72b452fa5f1792d9f2c[m[33m ([m[1;36mHEAD -> [m[1;32mmaster[m[33m, [m[1;31morigin/master[m[33m, [m[1;31morigin/HEAD[m[33m)[m
Author: lucasfdcampos <_83_wm_tz_>
Date:   Sat Dec 7 14:51:38 2019 -0300

    Change listener.

[1mdiff --git a/src/main/java/br/com/dashboard/Application.java b/src/main/java/br/com/dashboard/Application.java[m
[1mnew file mode 100644[m
[1mindex 0000000..94a47fa[m
[1m--- /dev/null[m
[1m+++ b/src/main/java/br/com/dashboard/Application.java[m
[36m@@ -0,0 +1,100 @@[m
[32m+[m[32mpackage br.com.dashboard;[m
[32m+[m
[32m+[m[32mimport java.io.*;[m
[32m+[m[32mimport java.nio.file.*;[m
[32m+[m[32mimport java.util.HashMap;[m
[32m+[m[32mimport java.util.Map;[m
[32m+[m[32mimport java.util.zip.ZipEntry;[m
[32m+[m[32mimport java.util.zip.ZipInputStream;[m
[32m+[m
[32m+[m[32mpublic class Application {[m
[32m+[m
[32m+[m[32m    private static final String directory = "C:\\xml";[m
[32m+[m
[32m+[m[32m    public static void main(String[] args) {[m
[32m+[m
[32m+[m[32m        try (WatchService service = FileSystems.getDefault().newWatchService()) {[m
[32m+[m[32m            Map<WatchKey, Path> keyMap = new HashMap<>();[m
[32m+[m[32m            Path path = Paths.get(directory);[m
[32m+[m[32m            keyMap.put(path.register(service,[m
[32m+[m[32m                    StandardWatchEventKinds.ENTRY_CREATE,[m
[32m+[m[32m                    StandardWatchEventKinds.ENTRY_DELETE,[m
[32m+[m[32m                    StandardWatchEventKinds.ENTRY_MODIFY),[m
[32m+[m[32m                    path);[m
[32m+[m
[32m+[m[32m            WatchKey watchKey;[m
[32m+[m
[32m+[m[32m            do {[m
[32m+[m[32m                watchKey = service.take();[m
[32m+[m[32m                Path eventDir = keyMap.get(watchKey);[m
[32m+[m
[32m+[m[32m                for (WatchEvent<?> event : watchKey.pollEvents()) {[m
[32m+[m[32m                    WatchEvent.Kind<?> kind = event.kind();[m
[32m+[m[32m                    Path eventPath = (Path) event.context();[m
[32m+[m
[32m+[m[32m                    if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {[m
[32m+[m[32m                        System.out.println(eventDir + ": " + kind + ": " + eventPath);[m
[32m+[m
[32m+[m[32m                        File file = eventPath.toFile();[m
[32m+[m[32m                        System.out.println("file: " + file);[m
[32m+[m
[32m+[m[32m                        if (eventPath.getFileName().toString().endsWith(".zip")) {[m
[32m+[m[32m                            File fileZip = new File(eventDir + File.separator + file);[m
[32m+[m[32m                            System.out.println("fileZip: " + fileZip);[m
[32m+[m
[32m+[m[32m                            unzip(fileZip.getAbsolutePath());[m
[32m+[m[32m                            fileZip.delete();[m
[32m+[m[32m                        }[m
[32m+[m
[32m+[m[32m                        if (eventPath.getFileName().toString().endsWith(".xml")) {[m
[32m+[m[32m                            File fileXml = new File(eventDir + File.separator + file);[m
[32m+[m[32m                            System.out.println("Arquivo xml criado: " + fileXml);[m
[32m+[m[32m                        }[m
[32m+[m[32m                    }[m
[32m+[m[32m                }[m
[32m+[m
[32m+[m[32m            } while (watchKey.reset());[m
[32m+[m
[32m+[m[32m        } catch (Exception e) {[m
[32m+[m[32m            System.out.println(e);[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    private static void unzip(String zipFilePath) throws IOException {[m
[32m+[m[32m        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));[m
[32m+[m[32m        ZipEntry entry = zipInputStream.getNextEntry();[m
[32m+[m
[32m+[m[32m        // iterates entries (xmls)[m
[32m+[m[32m        while (entry != null) {[m
[32m+[m[32m            String filePath = Application.directory + File.separator + entry.getName();[m
[32m+[m
[32m+[m[32m            if (!entry.isDirectory()) {[m
[32m+[m[32m                extractFile(zipInputStream, filePath);[m
[32m+[m
[32m+[m[32m            } else {[m
[32m+[m[32m                // caso seja um diretorio[m
[32m+[m[32m                File dir = new File(filePath);[m
[32m+[m[32m                dir.mkdir();[m
[32m+[m[32m            }[m
[32m+[m[32m            zipInputStream.closeEntry();[m
[32m+[m[32m            entry = zipInputStream.getNextEntry();[m
[32m+[m[32m        }[m
[32m+[m[32m        zipInputStream.close();[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    /**[m
[32m+[m[32m     * Extracts a zip entry (xml)[m
[32m+[m[32m     * @param zipInputStream[m
[32m+[m[32m     * @param filePath[m
[32m+[m[32m     * @throws IOException[m
[32m+[m[32m     */[m
[32m+[m[32m    private static void extractFile(ZipInputStream zipInputStream, String filePath) throws IOException {[m
[32m+[m[32m        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));[m
[32m+[m[32m        byte[] bytesIn = new byte[4096];[m
[32m+[m[32m        int read = 0;[m
[32m+[m[32m        while ((read = zipInputStream.read(bytesIn)) != -1) {[m
[32m+[m[32m            bos.write(bytesIn, 0, read);[m
[32m+[m[32m        }[m
[32m+[m[32m        bos.close();[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
\ No newline at end of file[m
