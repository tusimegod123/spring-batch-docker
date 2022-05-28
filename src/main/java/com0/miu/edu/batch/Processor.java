package com0.miu.edu.batch;

import com0.miu.edu.model.BatchFile;
import com0.miu.edu.model.BatchFileWritter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class Processor implements ItemProcessor<BatchFile, BatchFileWritter> {

    private static final Map<Integer, LocalDate> DOB =
            new HashMap<>();

    public Processor() {
        DOB.put(1, LocalDate.of(2022, 1, 1));
       DOB.put(2,  LocalDate.of(2022, 1, 1));

    }

    @Override
    public BatchFileWritter process(BatchFile batchFile) throws Exception {
        Integer dateCode = batchFile.getDOB();
        LocalDate date = DOB.get(dateCode);
        int now = LocalDate.now().getYear();
       now -= dateCode;
        LocalDate newDate = LocalDate.of(now, 1, 1);
        BatchFileWritter batchFileWritter = new BatchFileWritter();
        batchFileWritter.setFirst(batchFile.getFirst());
        batchFileWritter.setLast(batchFile.getLast());
        batchFileWritter.setGPA(batchFile.getGPA());
        batchFileWritter.setDOB(newDate);
        System.out.printf("Converted from [%s] to [%s]%n", dateCode, date);
        return batchFileWritter;
    }
}
