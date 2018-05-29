package com.rabbitshop.springbootadminserver.configs;

import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.MailNotifier;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Duration;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Configuration
@EnableScheduling
public class NotifierConfig {

	static final long REMINDER_PERIOD = 60L;

	@Bean("customFilteringNotifier")
	@Autowired
	public FilteringNotifier createFilteringNotifier(final MailNotifier mailNotifier, final InstanceRepository instanceRepository) {

		log.debug("Creating custom filterging notifier...");

		return new FilteringNotifier(mailNotifier, instanceRepository);
	}

	@Bean("customRemindingNotifier")
	@Primary
	@Autowired
	public RemindingNotifier createRemindingNotifier(final FilteringNotifier filteringNotifier, final InstanceRepository instanceRepository) {

		log.debug("Creating custom reminding notifier...");

		final RemindingNotifier remindingNotifier = new RemindingNotifier(filteringNotifier, instanceRepository);
		remindingNotifier.setReminderPeriod(Duration.ofSeconds(REMINDER_PERIOD));
		remindingNotifier.start();
		return remindingNotifier;
	}

}
