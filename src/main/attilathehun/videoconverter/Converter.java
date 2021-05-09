package attilathehun.videoconverter;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import attilathehun.videoconverter.MainFrame;

public class Converter {
    private String outputFileType;
    private String outputFileName;
    private File inputFile;
    private final String libBaseUrl = "/lib/";
    private final Map<String, String> libNameMap;
    private boolean conversionFinished = false;


    public Converter() {
        this.libNameMap = new ConcurrentHashMap<>();
        libNameMap.put("AVI", "AVIConversionLibrary");
        libNameMap.put("MKV", "MKVConversionLibrary");
        libNameMap.put("MP4", "MP4ConversionLibrary");
    }

    public void setOutputFileType(String outputFileType) {
        this.outputFileType = outputFileType;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public String getOutputFileType() {
        return outputFileType;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public void setConversionFinished(boolean conversionFinished) {
        this.conversionFinished = conversionFinished;
    }

    public void convert() {
        try {
            outputFileName = inputFile.getParent() + "/" + getOutputFileName() + "." + getOutputFileType().toLowerCase(Locale.ROOT);
            File outputFile = new File(outputFileName);
            outputFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(outputFile, false);
            InputStream sourceFileInputStream = Converter.class.getResourceAsStream(libBaseUrl + libNameMap.get(outputFileType));
            byte[] buffer = new byte[sourceFileInputStream.available()];
            sourceFileInputStream.read(buffer);
            outputStream.write(buffer);
            outputStream.close();
            sourceFileInputStream.close();
            this.setConversionFinished(true);
            MainFrame.logger.log("Conversion successful: "
            + outputFile.getAbsolutePath());
        }catch(Exception e) {
            e.printStackTrace(System.out);
            MainFrame.logger.log("Error while converting files. Check console for more info");
        }
    }

    public String getLibName(String lib){
        return libNameMap.get(lib);
    }
}
