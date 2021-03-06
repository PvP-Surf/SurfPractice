package surf.pvp.practice.kit;

public enum KitType {

    BUILD,
    NO_BUILD;

    public static KitType oppositeOf(KitType kitType) {
        if (kitType.equals(KitType.BUILD))
            return KitType.NO_BUILD;

        return KitType.BUILD;
    }

}
