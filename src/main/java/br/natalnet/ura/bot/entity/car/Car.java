package br.natalnet.ura.bot.entity.car;

import br.natalnet.ura.bot.entity.car.status.CarStatus;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Car {

    private final String carId;

    private final String carName;

    private final CarStatus status = CarStatus.UNKNOWN;
}
