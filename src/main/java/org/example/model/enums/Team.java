package org.example.model.enums;

public enum Team {
    NO_TEAM("noteam"),
    ORANGE_TEAM( "orange"),
    PINK_TEAM( "pink");

    private final String teamName;

    Team(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public static Team value(String teamName) {
        for (Team team : Team.values()) {
            if (team.teamName.equalsIgnoreCase(teamName) || team.name().equalsIgnoreCase(teamName)) {
                return team;
            }
        }
        throw new IllegalArgumentException("No enum constant with teamName " + teamName);
    }

    @Override
    public String toString() {
        return teamName;
    }
}
