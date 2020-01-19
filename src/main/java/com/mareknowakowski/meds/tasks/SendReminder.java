package com.mareknowakowski.meds.tasks;

import com.mareknowakowski.meds.domain.Medicine;
import com.mareknowakowski.meds.domain.Reminder;
import com.mareknowakowski.meds.model.PushNotificationRequest;
import com.mareknowakowski.meds.repositories.MedicineRepository;
import com.mareknowakowski.meds.repositories.ReminderRepository;
import com.mareknowakowski.meds.services.MedicineService;
import com.mareknowakowski.meds.services.PushNotificationService;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SendReminder {
    private static final String MESSAGE_BODY = "It is time for you to take your ";
    private static final String MESSAGE_TITLE = "Hey!";
    private static final String NEARLY_OUT_OF_MESSAGE_BODY = "You are nearly out of your ";

    private static final Logger log = LoggerFactory.getLogger(SendReminder.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE::HH:mm");


    private final ReminderRepository reminderRepository;
    private final MedicineRepository medicineRepository;
    private final MedicineService medicineService;
    private final PushNotificationService pushNotificationService;

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {


        Date date = new Date();
        log.info("The time now {}", dateFormat.format(new Date()));
    }

    @Scheduled(fixedRate = 60000)
    public void sendReminders() {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
        calendar.setTime(currentDate);
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        List<Reminder> reminders = reminderRepository.findByDayAndHour(Reminder.getDayMapping(day), hour + ":" + minute);
        reminders
                .forEach((Reminder r) -> {
                    log.info("sending reminder" + r.toString());
                    PushNotificationRequest request = new PushNotificationRequest(MESSAGE_TITLE, MESSAGE_BODY + r.getMedicine().getName(), "");
                    request.setToken(r.getMedicine().getUser().getDeviceId());
                    pushNotificationService.sendPushNotificationToToken(request);
                    try {
                        medicineService.lowerCountByOne(r.getMedicine().getId());
                    } catch (ChangeSetPersister.NotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Scheduled(fixedRate = 550000)
    public void sendNearlyOutOFReminder() {
        List<Medicine> medicines = medicineRepository.findAllByCountLessThanAndOutOfReminderAlreadySent(5, false);
        medicines.forEach((Medicine m) -> {
            PushNotificationRequest request = new PushNotificationRequest(NEARLY_OUT_OF_MESSAGE_BODY + m.getName(), "there is only: " + m.getCount() + " left", "");
            request.setToken(m.getUser().getDeviceId());
            pushNotificationService.sendPushNotificationToToken(request);
            try {
                medicineService.setAlreadySentFlagToTrue(m.getId());
            }catch (ChangeSetPersister.NotFoundException e){
                e.printStackTrace();
        }

    });

}



}
