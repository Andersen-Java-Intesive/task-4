package org.example.model.enums;

public enum Team {
    NO_TEAM("NO_TEAM", "noteam"),
    ORANGE_TEAM("ORANGE_TEAM", "orange"),
    PINK_TEAM("PINK_TEAM", "pink");

    private final String teamName;

    public String getAlterTeamName() {
        return alterTeamName;
    }

    private final String alterTeamName;

    Team(String teamName, String alterTeamName) {
        this.teamName = teamName;
        this.alterTeamName = alterTeamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public static Team fromString(String teamName) {
        for (Team team : Team.values()) {
            if (team.teamName.equalsIgnoreCase(teamName) || team.name().equalsIgnoreCase(teamName) || team.alterTeamName.equalsIgnoreCase(teamName)) {
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
