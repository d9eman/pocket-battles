package pocketbattles.strategy;

import pocketbattles.model.Type;

import java.util.List;

public interface TypeChart {
    double multiplier(Type attackingType, List<Type> defendingTypes);
}
