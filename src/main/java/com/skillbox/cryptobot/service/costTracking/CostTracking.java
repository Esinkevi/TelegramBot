package com.skillbox.cryptobot.service.costTracking;

import com.skillbox.cryptobot.model.Subscribers;


import java.util.List;

public interface CostTracking {
    List<Subscribers> searchSubscribers(double priceBtc);

    void updateNotificationTime(Subscribers subscribers);
}
