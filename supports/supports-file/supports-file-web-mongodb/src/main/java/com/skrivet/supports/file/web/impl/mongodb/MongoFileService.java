package com.skrivet.supports.file.web.impl.mongodb;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.skrivet.core.common.convert.IdGenerator;
import com.skrivet.core.common.exception.UnknownExp;
import com.skrivet.supports.file.web.core.FileService;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public class MongoFileService implements FileService {
    @Resource(name = "fileGridFsTemplate")
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private IdGenerator idGenerator;
    @Resource(name = "fileMongoDatabaseFactory")
    private MongoDatabaseFactory mongoDatabaseFactory;

    @Override
    public String storeFile(String fileName, InputStream inputStream, String contentType) {
        String type = fileName.substring(fileName.lastIndexOf(".") - 1, fileName.length());
        ObjectId id = gridFsTemplate.store(inputStream, idGenerator.generate() + type, contentType);
        return id.toString();
    }

    @Override
    public void writeFile(String id, OutputStream outputStream) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        GridFSFile gridFSFile = gridFsTemplate.findOne(query);
        GridFSBucket bucket = GridFSBuckets.create(mongoDatabaseFactory.getMongoDatabase());
        GridFSDownloadStream in = bucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource resource = new GridFsResource(gridFSFile, in);
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            IOUtils.closeQuietly(outputStream);
        } catch (Exception e) {
            throw new UnknownExp().copyStackTrace(e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(in);
        }
    }

}
