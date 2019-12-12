package br.com.dashboard;

import br.inf.portalfiscal.nfe.schema.nfe.TNfeProc;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Application {

    private static final String directory = "C:\\xml";

    public static void main(String[] args) {

        try (WatchService service = FileSystems.getDefault().newWatchService()) {
            Map<WatchKey, Path> keyMap = new HashMap<>();
            Path path = Paths.get(directory);
            keyMap.put(path.register(service,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY),
                    path);

            WatchKey watchKey;

            do {
                watchKey = service.take();
                Path eventDir = keyMap.get(watchKey);

                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path eventPath = (Path) event.context();

                    if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
                        System.out.println(eventDir + ": " + kind + ": " + eventPath);

                        File file = new File(eventDir + File.separator + eventPath.toFile());
                        System.out.println("file: " + file);

                        if (eventPath.getFileName().toString().endsWith(".zip")) {
                            System.out.println("fileZip: " + file);

                            unzip(file.getAbsolutePath());
                            file.delete();
                        }

                        if (eventPath.getFileName().toString().endsWith(".xml")) {
                            System.out.println("Arquivo xml criado: " + file);

                            readXml(file.getAbsolutePath());
                        }
                    }
                }

            } while (watchKey.reset());

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Unzip a zip file
     * @param zipFilePath
     * @throws IOException
     */
    private static void unzip(String zipFilePath) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipInputStream.getNextEntry();

        // iterates entries (xmls)
        while (entry != null) {
            String filePath = Application.directory + File.separator + entry.getName();

            if (!entry.isDirectory()) {
                extractFile(zipInputStream, filePath);

            } else {
                // caso seja um diretorio
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipInputStream.closeEntry();
            entry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }

    /**
     * Extracts a zip entry (xml)
     * @param zipInputStream
     * @param filePath
     * @throws IOException
     */
    private static void extractFile(ZipInputStream zipInputStream, String filePath) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = zipInputStream.read(bytesIn)) != -1) {
            bufferedOutputStream.write(bytesIn, 0, read);
        }
        bufferedOutputStream.close();
    }

    /**
     * Reads a xml file
     * @param xmlFile
     * @throws IOException
     */
    private static void readXml(String xmlFile) throws Exception {

        String xml = extractXml(xmlFile);

        TNfeProc wNfe = getNfe(xml);
        if (wNfe != null) {
            System.out.println("-- DADOS NFe ----------------------------");
            System.out.println("CHAVE..: " + wNfe.getProtNFe().getInfProt().getChNFe());
            System.out.println("cNF....: " + wNfe.getNFe().getInfNFe().getIde().getCNF());
            System.out.println("nNF....: " + wNfe.getNFe().getInfNFe().getIde().getNNF());
            System.out.println("natOp..: " + wNfe.getNFe().getInfNFe().getIde().getNatOp());
            System.out.println("dhEmi..: " + wNfe.getNFe().getInfNFe().getIde().getDhEmi());
            System.out.println("tpNF...: " + wNfe.getNFe().getInfNFe().getIde().getTpNF());

            System.out.println("-- EMIT ---------------------------------");
            System.out.println("CNPJ...: " + wNfe.getNFe().getInfNFe().getEmit().getCNPJ());
            System.out.println("IE.....: " + wNfe.getNFe().getInfNFe().getEmit().getIE());
            System.out.println("xNome..: " + wNfe.getNFe().getInfNFe().getEmit().getXNome());
            System.out.println("------ ENDER EMIT -----------------------");
            System.out.println("xLgr...: " + wNfe.getNFe().getInfNFe().getEmit().getEnderEmit().getXLgr());
            System.out.println("nRo....: " + wNfe.getNFe().getInfNFe().getEmit().getEnderEmit().getNro());
            System.out.println("xBairro: " + wNfe.getNFe().getInfNFe().getEmit().getEnderEmit().getXBairro());
            System.out.println("xMun...: " + wNfe.getNFe().getInfNFe().getEmit().getEnderEmit().getXMun());
            System.out.println("UF.....: " + wNfe.getNFe().getInfNFe().getEmit().getEnderEmit().getUF());
            System.out.println("CEP....: " + wNfe.getNFe().getInfNFe().getEmit().getEnderEmit().getCEP());

            System.out.println("-- DEST ---------------------------------");
            System.out.println("CNPJ.: " + wNfe.getNFe().getInfNFe().getDest().getCNPJ());
            System.out.println("IE...: " + wNfe.getNFe().getInfNFe().getDest().getIE());
            System.out.println("xNome: " + wNfe.getNFe().getInfNFe().getDest().getXNome());
            System.out.println("------ ENDER DEST -----------------------");
            System.out.println("xLgr...: " + wNfe.getNFe().getInfNFe().getDest().getEnderDest().getXLgr());
            System.out.println("nRo....: " + wNfe.getNFe().getInfNFe().getDest().getEnderDest().getNro());
            System.out.println("xBairro: " + wNfe.getNFe().getInfNFe().getDest().getEnderDest().getXBairro());
            System.out.println("xMun...: " + wNfe.getNFe().getInfNFe().getDest().getEnderDest().getXMun());
            System.out.println("UF.....: " + wNfe.getNFe().getInfNFe().getDest().getEnderDest().getUF());
            System.out.println("CEP....: " + wNfe.getNFe().getInfNFe().getDest().getEnderDest().getCEP());
            System.out.println("fone...: " + wNfe.getNFe().getInfNFe().getDest().getEnderDest().getFone());

            System.out.println("-- DET -----------------------------------");
            System.out.println(wNfe.getNFe().getInfNFe().getDet().size());
            for (int i=0; i < wNfe.getNFe().getInfNFe().getDet().size(); i++) {
                System.out.println("------ PROD ------------------------------");
                System.out.println("nItem..: " + wNfe.getNFe().getInfNFe().getDet().get(i).getNItem());
                System.out.println("xProd..: " + wNfe.getNFe().getInfNFe().getDet().get(i).getProd().getXProd());
                System.out.println("NCM....: " + wNfe.getNFe().getInfNFe().getDet().get(i).getProd().getNCM());
                System.out.println("uCom...: " + wNfe.getNFe().getInfNFe().getDet().get(i).getProd().getUCom());
                System.out.println("qCom...: " + wNfe.getNFe().getInfNFe().getDet().get(i).getProd().getQCom());
                System.out.println("vUnCom.: " + wNfe.getNFe().getInfNFe().getDet().get(i).getProd().getVUnCom());
                System.out.println("vProd..: " + wNfe.getNFe().getInfNFe().getDet().get(i).getProd().getVProd());
            }

            System.out.println("-- TOTAL ---------------------------------");
            System.out.println("vProd..: " + wNfe.getNFe().getInfNFe().getTotal().getICMSTot().getVProd());
            System.out.println("vNF....: " + wNfe.getNFe().getInfNFe().getTotal().getICMSTot().getVNF());

            System.out.println("-- TRANSP --------------------------------");
            System.out.println("modFrete: " + wNfe.getNFe().getInfNFe().getTransp().getModFrete());
            System.out.println("------ TRANSPORTA ------------------------");
            System.out.println("CNPJ...: " + wNfe.getNFe().getInfNFe().getTransp().getTransporta().getCNPJ());
            System.out.println("xNome..: " + wNfe.getNFe().getInfNFe().getTransp().getTransporta().getXNome());
            System.out.println("IE.....: " + wNfe.getNFe().getInfNFe().getTransp().getTransporta().getIE());
            System.out.println("xEnder.: " + wNfe.getNFe().getInfNFe().getTransp().getTransporta().getXEnder());
            System.out.println("xMun...: " + wNfe.getNFe().getInfNFe().getTransp().getTransporta().getXMun());
            System.out.println("UF.....: " + wNfe.getNFe().getInfNFe().getTransp().getTransporta().getUF());

            System.out.println("-- COBR ----------------------------------");
            System.out.println("nFat...: " + wNfe.getNFe().getInfNFe().getCobr().getFat().getNFat());
            System.out.println("vOrig..: " + wNfe.getNFe().getInfNFe().getCobr().getFat().getVOrig());
            System.out.println("vDesc..: " + wNfe.getNFe().getInfNFe().getCobr().getFat().getVDesc());
            System.out.println("vLiq...: " + wNfe.getNFe().getInfNFe().getCobr().getFat().getVLiq());
            System.out.println(wNfe.getNFe().getInfNFe().getCobr().getDup().size());
            for (int i=0; i < wNfe.getNFe().getInfNFe().getCobr().getDup().size(); i++) {
                System.out.println("------ DUP -------------------------------");
                System.out.println("nDup...: " + wNfe.getNFe().getInfNFe().getCobr().getDup().get(i).getNDup());
                System.out.println("dVenc..: " + wNfe.getNFe().getInfNFe().getCobr().getDup().get(i).getDVenc());
                System.out.println("vDup...: " + wNfe.getNFe().getInfNFe().getCobr().getDup().get(i).getVDup());
            }
        }
    }

    /**
     * Extracts the xml code from the file to a string
     * @param xmlFile
     * @return
     * @throws IOException
     */
    private static String extractXml(String xmlFile) throws IOException {
        String linha = "";
        StringBuilder xml = new StringBuilder();

        BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream(xmlFile)));

        while ((linha = in.readLine()) != null) {
            xml.append(linha);
        }
        in.close();

        return xml.toString();
    }

    @SuppressWarnings("unchecked")
    public static TNfeProc getNfe(String xml) throws Exception {
        try {
            JAXBContext context = JAXBContext.newInstance(TNfeProc.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            TNfeProc nfe = unmarshaller.unmarshal(new StreamSource(new StringReader(xml)), TNfeProc.class).getValue();
            return nfe;

        } catch (JAXBException e) {
            throw new Exception(e.toString());
        }
    }
}