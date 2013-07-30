package us.elron.sp.administration;

public enum ConfigLocation {

    SYSTEM("System"), //
    CHANNELS("Channels"), //
    SERVICES("Services");

    private String location;

    private ConfigLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

}
