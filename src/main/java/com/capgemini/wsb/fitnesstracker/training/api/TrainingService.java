package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingService {
    Optional<User> getAllTrainings(Long trainingId);

    Training createTraining(Training training);

    List<Training> getTrainingsFinishedAfter(Date dateTime);

    Optional<Training>  getTrainingById(Long trainingId);
}