package csulb.cecs323.model;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class IndividualAuthors extends csulb.cecs323.model.AuthoringEntities {
    @OneToMany (mappedBy = "authors",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private ArrayList<csulb.cecs323.model.AdHocTeamMembers> teamMembers;

    public IndividualAuthors() { }

    public IndividualAuthors(String name, String email) {
        super(name, email, "Individual Author");
    }

    public void addTeamMembers(csulb.cecs323.model.AdHocTeamMembers teamMember) {
        teamMembers.add(teamMember);
        teamMember.setIndividualAuthors(this);
    }

    public void removeTeamMembers(csulb.cecs323.model.AdHocTeamMembers teamMember) {
        if (this.teamMembers != null) {
            teamMembers.remove(teamMember);
            teamMember.setIndividualAuthors(null);
        }
    }

    public ArrayList<csulb.cecs323.model.AdHocTeamMembers> getTeamMembers() { return teamMembers; }

    public void setTeamMembers(ArrayList<csulb.cecs323.model.AdHocTeamMembers> teamMembers) { this.teamMembers = teamMembers; }
}