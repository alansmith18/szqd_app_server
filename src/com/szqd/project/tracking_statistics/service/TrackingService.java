package com.szqd.project.tracking_statistics.service;

import com.szqd.framework.model.Pager;
import com.szqd.project.tracking_statistics.model.TrackingDataDB;
import com.szqd.project.tracking_statistics.model.TrackingDataPOJO;
import com.szqd.project.tracking_statistics.model.TrackingEventPOJO;

import java.util.List;

/**
 * Created by like on 11/18/15.
 */
public interface TrackingService {

    public List<TrackingEventPOJO> listEvent(TrackingEventPOJO condition,Pager pager);

    public TrackingEventPOJO queryTrackingEventByIDFromDB(Long id);

    public TrackingEventPOJO queryTrackingEventByIDFromCache(Long id);

    public void upsertTrackingEvent(TrackingEventPOJO edit);

    public void addTracking(TrackingDataDB tracking);

    public List<TrackingDataPOJO> fetchTrackingForReport(TrackingDataPOJO query);

}
