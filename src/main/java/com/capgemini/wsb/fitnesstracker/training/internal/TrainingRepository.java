package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    default List<Training> getTrainingsFinishedAfter(Date date)
    {
        return findAll().stream().filter(
                training -> Objects.compare(training.getEndTime(), date, Comparator.naturalOrder()) > 0
        ).collect(Collectors.toList());
    }

    default List<Training> getTrainingsByType(ActivityType type)
    {
        return findAll().stream().filter(
                training -> Objects.equals(training.getActivityType(), type)
        ).collect(Collectors.toList());
    }

    default Training updateTraining(Training training)
    {
        return save(training);
    }

    default List<Training> getTrainingsByUser(User user) {
        return findAll().stream()
                .filter(training -> Objects.equals(training.getUser(), user))
                .collect(Collectors.toList());
    }

}
