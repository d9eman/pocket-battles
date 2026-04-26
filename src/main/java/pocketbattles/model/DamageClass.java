package pocketbattles.model;

public enum DamageClass {
    PHYSICAL {
        @Override
        public int attackingStat(Pokemon pokemon) {
            return pokemon.getEffectiveAttack();
        }

        @Override
        public int defendingStat(Pokemon pokemon) {
            return pokemon.getEffectiveDefense();
        }
    },
    SPECIAL {
        @Override
        public int attackingStat(Pokemon pokemon) {
            return pokemon.getEffectiveSpecialAttack();
        }

        @Override
        public int defendingStat(Pokemon pokemon) {
            return pokemon.getEffectiveSpecialDefense();
        }
    };

    public abstract int attackingStat(Pokemon pokemon);
    public abstract int defendingStat(Pokemon pokemon);
}
