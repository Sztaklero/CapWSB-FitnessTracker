package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {

    public Training toEntity(TrainingDtoWithUserId trainingDto, User user) {
        if (trainingDto.id() == null) {
            return new Training(
                    user, trainingDto.startTime(),
                    trainingDto.endTime(), trainingDto.activityType(),
                    trainingDto.distance(), trainingDto.averageSpeed()
            );
        } else {
            return new Training(
                    trainingDto.id(), user, trainingDto.startTime(),
                    trainingDto.endTime(), trainingDto.activityType(),
                    trainingDto.distance(), trainingDto.averageSpeed()
            );
        }
    }

    public TrainingDto toDto(Training training) {
        return new TrainingDto(
                training.getId(), training.getUser(), training.getStartTime(),
                training.getEndTime(), training.getActivityType(), training.getDistance(),
                training.getAverageSpeed()
        );
    }

    public Training toEntity(TrainingDto trainingDto) {
        if (trainingDto.id() == null) {
            return new Training(
                    trainingDto.user(), trainingDto.startTime(),
                    trainingDto.endTime(), trainingDto.activityType(),
                    trainingDto.distance(), trainingDto.averageSpeed()
            );
        } else {
            return new Training(
                    trainingDto.id(), trainingDto.user(), trainingDto.startTime(),
                    trainingDto.endTime(), trainingDto.activityType(),
                    trainingDto.distance(), trainingDto.averageSpeed()
            );
        }
    }

    public Training toEntity(TrainingDto trainingDto, Long id) {
        return new Training(
                id, trainingDto.user(), trainingDto.startTime(),
                trainingDto.endTime(), trainingDto.activityType(),
                trainingDto.distance(), trainingDto.averageSpeed()
        );
    }

    public Training toEntity(TrainingDto trainingDto, Long id, User user) {
        return new Training(
                id, user, trainingDto.startTime(),
                trainingDto.endTime(), trainingDto.activityType(),
                trainingDto.distance(), trainingDto.averageSpeed()
        );
    }
}