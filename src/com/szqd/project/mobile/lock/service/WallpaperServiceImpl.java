package com.szqd.project.mobile.lock.service;


import com.szqd.framework.model.FileEntity;
import com.szqd.framework.model.Pager;
import com.szqd.framework.model.Resolution;
import com.szqd.framework.model.Scale;
import com.szqd.framework.service.SuperService;
import com.szqd.project.mobile.lock.model.WallpaperEntity;
import com.szqd.project.mobile.lock.model.WallpaperTypeEntity;
import net.coobird.thumbnailator.Thumbnails;
import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


import java.io.File;
import java.util.*;

/**
 * Created by like on 4/7/15.
 */
public class WallpaperServiceImpl extends SuperService implements WallpaperService
{


    private static final Logger logger = Logger.getLogger(WallpaperServiceImpl.class);

    /**
     * 添加壁纸类别
     * @param wallpaperType
     */
    public void addWallpaperType(WallpaperTypeEntity wallpaperType)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Long sequenceForWallpaperType = this.getNextSequenceForFieldKey(WallpaperTypeEntity.ENTITY_NAME);
            wallpaperType.setId(sequenceForWallpaperType);
            mongoTemplate.insert(wallpaperType, WallpaperTypeEntity.ENTITY_NAME);
        } catch (Exception e)
        {
            logger.error("com.szqd.project.mobile.lock.service.WallpaperServiceImpl.addWallpaperType(WallpaperTypeEntity wallpaperType)",e);
            throw new RuntimeException("添加壁纸类型出错");
        }
    }

    /**
     * 检查壁纸类别的名称是否重复
     * @param wallpaperType
     * @return
     */
    public boolean checkDuplicatePaperType(WallpaperTypeEntity wallpaperType)
    {

        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query(Criteria.where("typeName").is(wallpaperType.getTypeName()));
            Long count = mongoTemplate.count(query,WallpaperTypeEntity.class,WallpaperTypeEntity.ENTITY_NAME);
            if (new Long(0).equals(count))
            {
                return true;
            }
        }
        catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.WallpaperServiceImpl.checkDuplicatePaperType(WallpaperTypeEntity wallpaperType)",e);
            throw new RuntimeException("检查类别名称重复出错");
        }

        return false;
    }

    /**
     * 查询所有的壁纸类型
     * @return
     */
    public List<WallpaperTypeEntity> listAllWallpaperType()
    {
        List<WallpaperTypeEntity> list = null;
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            list = mongoTemplate.findAll(WallpaperTypeEntity.class, WallpaperTypeEntity.ENTITY_NAME);
        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.WallpaperServiceImpl.listAllWallpaperType()",e);
            throw new RuntimeException("查询所有的壁纸的类型出错");
        }
        return list;
    }

    /**
     * 通过壁纸类别罗列壁纸
     * @param paperType
     * @return
     */
    public List<WallpaperEntity> listWallpaperByType(Integer paperType)
    {
        List<WallpaperEntity> list = null;
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            String queryString = "{pagerType:%s}";
            queryString = String.format(queryString,paperType);
            String fields = "{thumbnails : 1, downloadCount : 1}";
            BasicQuery query = new BasicQuery(queryString,fields);
            list = mongoTemplate.find(query, WallpaperEntity.class, WallpaperEntity.ENTITY_NAME);
        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.WallpaperServiceImpl.listWallpaperByType(Integer paperType)",e);
            throw new RuntimeException("通过壁纸类别罗列壁纸出错");
        }
        return list;
    }

    /**
     * 通过壁纸类别根据分页罗列壁纸
     * @param paperType
     * @param pager
     * @return
     */
    public List<WallpaperEntity> listWallpaperWithPageByType(Integer paperType,Pager pager)
    {
        List<WallpaperEntity> list = null;
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            String queryString = "{pagerType:%s}";
            queryString = String.format(queryString,paperType);
            String fields = "{thumbnails : 1}";
            BasicQuery query = new BasicQuery(queryString,fields);
            query.skip(pager.getOffset());
            query.limit(pager.getCapacity());
            list = mongoTemplate.find(query, WallpaperEntity.class, WallpaperEntity.ENTITY_NAME);
        }
        catch (Exception e)
        {
            logger.error("com.szqd.project.mobile.lock.service.WallpaperServiceImpl.listWallpaperByType(Integer paperType)",e);
            throw new RuntimeException("通过壁纸类别根据分页罗列壁纸");
        }
        return list;
    }

    private static final float RESOLUTION_THUMBNAILSKEY = -300.468f;
    /**
     * 根据源图比例生成要缩放的分辨率
     * @param scale
     * @return
     */
    public List<Resolution> generateResoulutionList(float scale)
    {
        List<Resolution> generateResoulutionList = null;
        try {
            generateResoulutionList = new ArrayList<Resolution>();
            if (scale == Scale.SCALE16$9.getValue())
            {

                Resolution resolution720p = new Resolution();
                resolution720p.setKey(1280.720f);
                resolution720p.setWidth(1280);
                resolution720p.setHeight(720);
                generateResoulutionList.add(resolution720p);

                Resolution resolution960$540 = new Resolution();
                resolution960$540.setKey(960.540f);
                resolution960$540.setWidth(960);
                resolution960$540.setHeight(540);
                generateResoulutionList.add(resolution960$540);

                Resolution resolution854$480 = new Resolution();
                resolution854$480.setKey(854.480f);
                resolution854$480.setWidth(854);
                resolution854$480.setHeight(480);
                generateResoulutionList.add(resolution854$480);

            }
            else if (scale == Scale.SCALE4$3.getValue())
            {

                Resolution resolution320$240 = new Resolution();
                resolution320$240.setKey(320.240f);
                resolution320$240.setWidth(320);
                resolution320$240.setHeight(240);
                generateResoulutionList.add(resolution320$240);

                Resolution resolution640$480 = new Resolution();
                resolution640$480.setKey(640.480f);
                resolution640$480.setWidth(640);
                resolution640$480.setHeight(480);
                generateResoulutionList.add(resolution640$480);
            }
            else if (scale == Scale.SCALE5$3.getValue())
            {
                Resolution resolution495$297 = new Resolution();
                resolution495$297.setKey(495.297f);
                resolution495$297.setWidth(495);
                resolution495$297.setHeight(297);
                resolution495$297.setQuality(0.8f);
                generateResoulutionList.add(resolution495$297);
            }

        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.WallpaperServiceImpl.generateResoulutionList(float scale)",e);
            throw new RuntimeException("根据源图比例生成要缩放的分辨率出错");
        }

        return generateResoulutionList;
    }

    /**
     * 根据目标分辨率 生成所有图片
     * @param sourceFileEntity
     * @param resolutionList
     * @return 返回生成图片的相关信息
     */
    public List<FileEntity> scaleImage(FileEntity sourceFileEntity,List<Resolution> resolutionList)
    {
        List<FileEntity> fileEntityList = new ArrayList<FileEntity>();
        try {
            String sourceFilePath = sourceFileEntity.getRealPath();
            String sourceRelativeFolderPath = sourceFileEntity.getRelativeFolderPath();
            String fileTypeName = sourceFileEntity.getFileType();
            String sourceRealFolderPath = sourceFileEntity.getRealFolderPath();

            for (int i = 0; i < resolutionList.size(); i++)
            {
                Resolution resolutionVar = resolutionList.get(i);
                float key = resolutionVar.getKey();
                int height = (int)resolutionVar.getHeight();
                int width = (int)resolutionVar.getWidth();
                Float quality = resolutionVar.getQuality();
                if (quality == null)
                {
                    quality = 1.0f;
                }

                String fileName = UUID.randomUUID().toString()+fileTypeName;

                StringBuilder realFilePath = new StringBuilder();
                realFilePath.append(sourceRealFolderPath).append(fileName);

                StringBuilder relativeFilePath = new StringBuilder();
                relativeFilePath.append(sourceRelativeFolderPath).append(fileName);

                Thumbnails.of(sourceFilePath).size(height,width).outputQuality(quality).toFile(realFilePath.toString());

                FileEntity fileEntity = new FileEntity();
                fileEntity.setRelativePath(relativeFilePath.toString());
                fileEntity.setResolution(resolutionVar);
                fileEntityList.add(fileEntity);
            }

            return fileEntityList;
        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.WallpaperServiceImpl.scaleImage(FileEntity sourceFileEntity,List<Resolution> resolutionList)",e);
            throw new RuntimeException("根据目标分辨率,生成所有图片出错");
        }
    }

    /**
     * 添加壁纸信息
     * @param wallpaperUpload4$3
     * @param wallpaperUpload5$3
     * @param wallpaperUpload16$9
     * @param paperType
     */
    public void addWallpaper(Long effectiveTime,String wallpaperUpload4$3, String wallpaperUpload5$3, String wallpaperUpload16$9,Long paperType)
    {
        try {
            WallpaperEntity wallpaper = new WallpaperEntity();
            wallpaper.setEffectiveTime(effectiveTime);
            wallpaper.setPagerType(paperType);
            wallpaper.setDownloadCount(0);
            wallpaper.setCreateTime(Calendar.getInstance().getTimeInMillis());
            wallpaper.setId(this.getNextSequenceForFieldKey(WallpaperEntity.ENTITY_NAME));
            List<MorphDynaBean> file43 = this.getFileEntityByJsonString(wallpaperUpload4$3);
            List<MorphDynaBean> file53 = this.getFileEntityByJsonString(wallpaperUpload5$3);
            List<MorphDynaBean> file169 = this.getFileEntityByJsonString(wallpaperUpload16$9);
            List<MorphDynaBean> allFile = new ArrayList<MorphDynaBean>();
            allFile.addAll(file169);
            allFile.addAll(file53);
            allFile.addAll(file43);

            for (MorphDynaBean fileVar :allFile)
            {
                MorphDynaBean resolution = (MorphDynaBean)fileVar.get("resolution");
                final double resolutionKey = (Double)resolution.get("key");//fileVar.getResolution().getKey();
                String relativePath = (String)fileVar.get("relativePath");//.getRelativePath();
                if (resolutionKey == 320.24)//320x240
                {
                    wallpaper.setUrlQvga(relativePath);
                }
                else if (resolutionKey == 640.48)//640x480
                {
                    wallpaper.setUrlVga(relativePath);
                }
                else if (resolutionKey == 1280.72)//1280x720
                {
                    wallpaper.setUrl720p(relativePath);
                }
                else if (resolutionKey == 960.54)//960x540
                {
                    wallpaper.setUrlQhd(relativePath);
                }
                else if (resolutionKey == 854.48)//854x480
                {
                    wallpaper.setUrlFwvga(relativePath);
                }
                else if (resolutionKey == 1920.108)//1920x1080
                {
                    wallpaper.setUrl1080p(relativePath);
                }
                else if (resolutionKey == 800.6)//800x600
                {
                    wallpaper.setUrlSvga(relativePath);
                }
                else if (resolutionKey == 800.48)//800x480
                {
                    wallpaper.setUrlWvga(relativePath);

                }
                else if (resolutionKey == 495.297) //495x297
                {
                    wallpaper.setThumbnails(relativePath);
                }
            }

            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            mongoTemplate.insert(wallpaper, WallpaperEntity.ENTITY_NAME);
        } catch (Exception e)
        {
            logger.error("com.szqd.project.mobile.lock.service.WallpaperServiceImpl.addWallpaper(String wallpaperUpload4$3, String wallpaperUpload5$3, String wallpaperUpload16$9)",e);
            throw new RuntimeException("添加壁纸信息出错");
        }
    }

    /**
     * 转换json字符串为FileEntity对象
     * @param json
     * @return
     */
    public List<MorphDynaBean> getFileEntityByJsonString(String json)
    {
        JSONObject jsonObject = JSONObject.fromObject(json);
        MorphDynaBean map = (MorphDynaBean)JSONObject.toBean(jsonObject);
        MorphDynaBean resultData = (MorphDynaBean)map.get("resultData");
        return (List<MorphDynaBean>)resultData.get("scalePhotos");
    }


    /**
     * 删除壁纸
     * @param wallpaperID
     * @param contextRealPath
     */
    public void deleteWallpaperByID(Integer wallpaperID,String contextRealPath)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(wallpaperID));
            List<WallpaperEntity> deleteWallpaperList = mongoTemplate.findAllAndRemove(query, WallpaperEntity.class, WallpaperEntity.ENTITY_NAME);
            for (WallpaperEntity wallpaper : deleteWallpaperList)
            {
                String url1080p = wallpaper.getUrl1080p();
                String url720p = wallpaper.getUrl720p();
                String urlFwvga = wallpaper.getUrlFwvga();

                String urlQhd = wallpaper.getUrlQhd();
                String urlQvga = wallpaper.getUrlQvga();
                String urlSvga = wallpaper.getUrlSvga();
                String urlVga = wallpaper.getUrlVga();
                String urlWvga = wallpaper.getUrlWvga();


                FileUtils.deleteQuietly(new File(contextRealPath + url1080p));

                FileUtils.deleteQuietly(new File(contextRealPath + url720p));

                FileUtils.deleteQuietly(new File(contextRealPath + urlFwvga));

                FileUtils.deleteQuietly(new File(contextRealPath + urlQhd));

                FileUtils.deleteQuietly(new File(contextRealPath + urlQvga));

                FileUtils.deleteQuietly(new File(contextRealPath + urlSvga));

                FileUtils.deleteQuietly(new File(contextRealPath + urlVga));

                FileUtils.deleteQuietly(new File(contextRealPath + urlWvga));

            }

        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.WallpaperServiceImpl.deleteWallpaperByID(Integer wallpaperID)",e);
            throw new RuntimeException("删除壁纸出错");
        }
    }

    /**
     * 通过wallpaperID查找壁纸
     * @param wallpaperID
     * @return
     */
    public WallpaperEntity findWallpaperByID(Integer wallpaperID)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(wallpaperID));
            List<WallpaperEntity> list = mongoTemplate.find(query, WallpaperEntity.class, WallpaperEntity.ENTITY_NAME);
            boolean isError = list == null || list.size() != 1;
            if (isError)
            {
                throw new RuntimeException("通过wallpaperID查找壁纸出错");
            }

            return list.get(0);
        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.WallpaperServiceImpl.findWallpaperByID(Integer wallpaperID)",e);
            throw new RuntimeException("通过wallpaperID查找壁纸出错");
        }

    }

    private static final String RECOMMEND_TYPE_NAME = "推荐";
    /**
     * 通过指定排序类型,和分页条件,来进行分页查询壁纸
     * @param pager
     * @param orderType
     * @param queryCondition
     * @return
     */
    public List<WallpaperEntity> findWallpaperByPager(Pager<WallpaperEntity> pager,int orderType,WallpaperEntity queryCondition)
    {
        try
        {

            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();

            Query query = new Query();

            String sortField = null;
            Sort.Direction direction = null;
            if (orderType == 1)//根据最新添加排序
            {
                sortField = "createTime";
                direction = Sort.Direction.DESC;
            }
            else if (orderType == 2)//根据人工推荐排序
            {
                sortField = "createTime";
                direction = Sort.Direction.DESC;

                String queryString = "{typeName:'%s'}";
                queryString = String.format(queryString,RECOMMEND_TYPE_NAME);
                String fields = "{}";
                BasicQuery basicQuery = new BasicQuery(queryString,fields);
                WallpaperTypeEntity recommendType = mongoTemplate.findOne(basicQuery,WallpaperTypeEntity.class,WallpaperTypeEntity.ENTITY_NAME);
                if (recommendType != null)
                {
                    queryCondition = new WallpaperEntity();
                    queryCondition.setPagerType(recommendType.getId());
                }
            }
            else if (orderType == 3)
            {
                sortField = "downloadCount";
                direction = Sort.Direction.DESC;
            }

            Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH)+1;
            cal.set(Calendar.DAY_OF_MONTH,day);
            cal.set(Calendar.HOUR_OF_DAY,0);
            cal.set(Calendar.MINUTE,0);
            cal.set(Calendar.SECOND,0);
            cal.set(Calendar.MILLISECOND,0);

            Criteria criteria = Criteria.where("effectiveTime").lte(cal.getTimeInMillis());

            if (queryCondition != null && queryCondition.getPagerType() != null)
            {
                criteria.and("pagerType").is(queryCondition.getPagerType());
            }
            query.addCriteria(criteria);

            Sort sort = new Sort(direction,sortField);
            query.with(sort);
            query.skip(pager.getOffset());
            query.limit(pager.getCapacity());

            List<WallpaperEntity> wallpaperList = mongoTemplate.find(query, WallpaperEntity.class, WallpaperEntity.ENTITY_NAME);
            return wallpaperList;
        }
        catch (Exception e)
        {
            logger.error("com.szqd.project.mobile.lock.service.WallpaperServiceImpl.findWallpaperByPager(Pager<WallpaperEntity> pager,int orderType,WallpaperEntity queryCondition)",e);
            throw new RuntimeException("通过指定排序类型,和分页条件,来进行分页查询壁纸出错");
        }

    }

    /**
     * 获取每个类别壁纸数量
     * @return
     */
    public List<HashMap> queryKindOfWallpaperCount()
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            GroupOperation groupOperation = Aggregation.group("pagerType").count().as("paperCount");
            ProjectionOperation projectionOperation = Aggregation.project("paperCount");
            Aggregation aggregation = Aggregation.newAggregation(groupOperation, projectionOperation);
            AggregationResults<HashMap> aggregationResults = mongoTemplate.aggregate(aggregation, WallpaperEntity.ENTITY_NAME, HashMap.class);
            List<HashMap> resultList = aggregationResults.getMappedResults();
            return resultList;
        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.WallpaperServiceImpl.queryWallpaperCountOfType()",e);
            throw new RuntimeException("获取每个类别壁纸数量出错");
        }
    }

    /**
     * 更新壁纸的下载次数
     * @param wallpaperID
     */
    public void updateWallpaperDownloadCount(Integer wallpaperID)
    {

        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();

            if (wallpaperID == null)
            {
                throw new RuntimeException("wallpaperID is null");
            }
            query.addCriteria(Criteria.where("id").is(wallpaperID));
            Update update = new Update();
            update.inc("downloadCount",1);
            mongoTemplate.findAndModify(query,update,WallpaperEntity.class,WallpaperEntity.ENTITY_NAME);
        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.WallpaperServiceImpl.updateWallpaperDownloadCount(Integer wallpaperID)",e);
            throw new RuntimeException("更新壁纸的下载次数出错");
        }
    }


    /**
     * 修改分类名称
     * @param wallpaperType
     */
    public void updateWallPaperCategoryName(WallpaperTypeEntity wallpaperType)
    {
        try {
            Long id = wallpaperType.getId();
            if (id == null) throw new RuntimeException("修改壁纸类别名称");

            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(id));
            Update update = new Update();
            update.set("typeName",wallpaperType.getTypeName());
            mongoTemplate.findAndModify(query,update,WallpaperTypeEntity.class,WallpaperTypeEntity.ENTITY_NAME);
        } catch (RuntimeException e) {
            logger.error("com.szqd.project.mobile.lock.service.WallpaperServiceImpl.updateWallPaperCategoryName(WallpaperTypeEntity wallpaperType)",e);
            throw new RuntimeException("修改分类名称出错");
        }

    }

    /**
     * 更新下载次数
     * @param wallpaper
     */
    public void updateWallpaperDownloadCount(WallpaperEntity wallpaper)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = Query.query(Criteria.where("id").is(wallpaper.getId()));
            Update update = new Update();
            update.set("downloadCount",wallpaper.getDownloadCount());
            mongoTemplate.findAndModify(query,update,WallpaperEntity.class,WallpaperEntity.ENTITY_NAME);
        }
        catch (Exception e)
        {
            logger.error("com.szqd.project.mobile.lock.service.WallpaperServiceImpl.updateWallpaperDownloadCount(WallpaperEntity wallpaper)",e);
            throw new RuntimeException("更新下载次数出错");
        }
    }

    /**
     * 更新推荐到id为1的类别
     */
    public void updateWallpaperCategoryForRecommendation()
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();

            String queryString = "{typeName:'%s'}";
            queryString = String.format(queryString,RECOMMEND_TYPE_NAME);
            String fields = "{}";
            BasicQuery basicQuery = new BasicQuery(queryString,fields);
            WallpaperTypeEntity recommendType = mongoTemplate.findOne(basicQuery,WallpaperTypeEntity.class,WallpaperTypeEntity.ENTITY_NAME);

            Calendar now = Calendar.getInstance();
            Query query = Query.query(Criteria.where("effectiveTime").lt(now.getTimeInMillis()).and("pagerType").is(recommendType.getId()));
            Update update = new Update();
            update.set("pagerType",1);
            mongoTemplate.updateMulti(query,update,WallpaperEntity.class,WallpaperEntity.ENTITY_NAME);

        }
        catch (Exception e)
        {
            logger.error("com.szqd.project.mobile.lock.service.WallpaperServiceImpl.updateWallpaperCategoryForRecommendation()",e);
            throw new RuntimeException("更新推荐到id为1的类别出错");
        }
    }


}



