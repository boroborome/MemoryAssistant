package com.happy3w.memoryassistant.service;

import com.happy3w.footstone.svc.IIDGeneratorSvc;
import com.happy3w.memoryassistant.model.MAInfoKey;
import com.happy3w.memoryassistant.model.MAInformation;
import com.happy3w.memoryassistant.model.MAKeyword;
import com.happy3w.memoryassistant.repository.MAInfoKeyRepository;
import com.happy3w.memoryassistant.repository.MAInformationRepository;
import com.happy3w.memoryassistant.repository.MAKeywordRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class MemoryService {
    private final MAKeywordRepository maKeywordRepository;
    private final MAInformationRepository maInformationRepository;
    private final MAInfoKeyRepository maInfoKeyRepository;
    private final IIDGeneratorSvc iidGeneratorSvc;

    public MemoryService(MAKeywordRepository maKeywordRepository,
                         MAInformationRepository maInformationRepository,
                         MAInfoKeyRepository maInfoKeyRepository,
                         IIDGeneratorSvc iidGeneratorSvc) {
        this.maKeywordRepository = maKeywordRepository;
        this.maInformationRepository = maInformationRepository;
        this.maInfoKeyRepository = maInfoKeyRepository;
        this.iidGeneratorSvc = iidGeneratorSvc;
    }

    public void exportData(File baseDir) throws IOException {
        exportFileData(maKeywordRepository.findAll(),
                "keyword.csv",
                new String[]{"id", "keyword"},
                data -> new String[]{Long.toString(data.getId()), data.getKeyword()},
                baseDir
                );

        MAInformation.DbConverter converter = new MAInformation.DbConverter();
        exportFileData(maInformationRepository.findAll(),
                "info.csv",
                new String[]{"id", "keywords", "content"},
                data -> new String[]{Long.toString(data.getId()),
                        converter.convertToDatabaseColumn(data.getLstKeyword()),
                        data.getContent()},
                baseDir
        );

        exportFileData(maInfoKeyRepository.findAll(),
                "relation.csv",
                new String[]{"infoId", "keyId"},
                data -> new String[]{Long.toString(data.getInfoid()), Long.toString(data.getWordid())},
                baseDir
        );
    }

    private <T> void exportFileData(List<T> datas,
                                    String fileName,
                                    String[] heads,
                                    Function<T, String[]> lineFormatter,
                                    File baseDir) throws IOException {
        Path filePath = Paths.get(baseDir.getAbsolutePath(), fileName);
        CSVWriter writer = new CSVWriter(new PrintWriter(Files.newOutputStream(filePath)));
        writer.writeNext(heads);
        for (T data : datas) {
            writer.writeNext(lineFormatter.apply(data));
        }
        writer.flush();
        writer.close();
    }

    public void importData(File baseDir) throws IOException {
        maKeywordRepository.deleteAll();
        maInformationRepository.deleteAll();
        maInfoKeyRepository.deleteAll();

        importFileData("keyword.csv",
                datas -> MAKeyword.builder()
                        .id(Long.parseLong(datas[0]))
                        .keyword(datas[1])
                        .build(),
                maKeywordRepository::save,
                baseDir);

        MAInformation.DbConverter converter = new MAInformation.DbConverter();
        importFileData("info.csv",
                datas -> MAInformation.builder()
                        .id(Long.parseLong(datas[0]))
                        .lstKeyword(converter.convertToEntityAttribute(datas[1]))
                        .content(datas[2])
                        .build(),
                maInformationRepository::save,
                baseDir);
        importFileData("relation.csv",
                datas -> MAInfoKey.builder()
                        .infoid(Long.parseLong(datas[0]))
                        .wordid(Long.parseLong(datas[1]))
                        .build(),
                maInfoKeyRepository::save,
                baseDir);

        iidGeneratorSvc.resetAll();
    }

    private <T> void importFileData(String fileName,
                                    Function<String[], T> assembler,
                                    Consumer<T> saver,
                                    File baseDir) throws IOException {
        Path filePath = Paths.get(baseDir.getAbsolutePath(), fileName);
        CSVReader reader = new CSVReader(Files.newBufferedReader(filePath));
        Iterator<String[]> lineIt = reader.iterator();
        lineIt.next();

        while (lineIt.hasNext()) {
            String[] datas = lineIt.next();
            T data = assembler.apply(datas);
            saver.accept(data);
        }
    }
}
