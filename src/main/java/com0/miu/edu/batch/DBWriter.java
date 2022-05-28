package com0.miu.edu.batch;

import com0.miu.edu.model.BatchFile;
import com0.miu.edu.model.BatchFileWritter;
import com0.miu.edu.repository.BatchFileRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<BatchFileWritter> {


    private BatchFileRepository batchFileRepository;

    @Autowired
    public DBWriter (BatchFileRepository batchFileRepository) {
        this.batchFileRepository = batchFileRepository;
    }

    @Override
    public void write(List<? extends BatchFileWritter> batchFiles) throws Exception{
        System.out.println("Data Saved for Files: " + batchFiles);
        batchFileRepository.saveAll(batchFiles);
    }
}
