package net.cis.service;

import java.util.List;

import net.cis.dto.NotificationHistoryDto;

public interface NotificationHistoryService {
	NotificationHistoryDto save(NotificationHistoryDto notificationHistoryDto);

	List<NotificationHistoryDto> findAllByCreatedBy(long createdBy);
}
