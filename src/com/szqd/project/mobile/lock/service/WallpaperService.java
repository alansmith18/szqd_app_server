package com.szqd.project.mobile.lock.service;

import com.szqd.framework.model.FileEntity;
import com.szqd.framework.model.Pager;
import com.szqd.framework.model.Resolution;
import com.szqd.project.mobile.lock.model.WallpaperEntity;
import com.szqd.project.mobile.lock.model.WallpaperTypeEntity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by like on 4/7/15.
 */
public interface WallpaperService {

    /**
     * 添加壁纸类别
     * @param wallpaperType
     */
    public void addWallpaperType(WallpaperTypeEntity wallpaperType);

    /**
     * 检查壁纸类别的名称是否重复
     * @param wallpaperType
     * @return
     */
    public boolean checkDuplicatePaperType(WallpaperTypeEntity wallpaperType);

    /**
     * 查询所有的壁纸类型
     * @return
     */
    public List<WallpaperTypeEntity> listAllWallpaperType();


    /**
     * 根据源图比例生成要缩放的分辨率
     * @param scale
     * @return
     */
    public List<Resolution> generateResoulutionList(float scale);

    /**
     * 根据目标分辨率 生成所有图片
     * @param sourceFileEntity
     * @param resolutionList
     * @return 返回生成图片的相关信息
     */
    public List<FileEntity> scaleImage(FileEntity sourceFileEntity,List<Resolution> resolutionList);

    /**
     * 通过壁纸类别罗列壁纸
     * @param paperType
     * @return
     */
    public List<WallpaperEntity> listWallpaperByType(Integer paperType);

    /**
     * 通过壁纸类别根据分页罗列壁纸
     * @param paperType
     * @param pager
     * @return
     */
    public List<WallpaperEntity> listWallpaperWithPageByType(Integer paperType,Pager pager);

    /**
     * 添加壁纸信息
     * @param wallpaperUpload4$3
     * @param wallpaperUpload5$3
     * @param wallpaperUpload16$9
     * @param paperType
     */
    public void addWallpaper(Long effectiveTime,String wallpaperUpload4$3, String wallpaperUpload5$3, String wallpaperUpload16$9,Long paperType);


    /**
     * 删除壁纸
     * @param wallpaperID
     * @param contextRealPath
     */
    public void deleteWallpaperByID(Integer wallpaperID,String contextRealPath);

    /**
     * 通过wallpaperID查找壁纸
     * @param wallpaperID
     * @return
     */
    public WallpaperEntity findWallpaperByID(Integer wallpaperID);

    /**
     * 通过指定排序类型,和分页条件,来进行分页查询壁纸
     * @param pager
     * @param orderType
     * @param queryCondition
     * @return
     */
    public List<WallpaperEntity> findWallpaperByPager(Pager<WallpaperEntity> pager,int orderType,WallpaperEntity queryCondition);

    /**
     * 获取每个类别壁纸数量
     * @return
     */
    public List<HashMap> queryKindOfWallpaperCount();

    /**
     * 更新壁纸的下载次数
     * @param wallpaperID
     */
    public void updateWallpaperDownloadCount(Integer wallpaperID);


    /**
     * 修改分类名称
     * @param wallpaperType
     */
    public void updateWallPaperCategoryName(WallpaperTypeEntity wallpaperType);

    /**
     * 更新下载次数
     * @param wallpaper
     */
    public void updateWallpaperDownloadCount(WallpaperEntity wallpaper);
}
