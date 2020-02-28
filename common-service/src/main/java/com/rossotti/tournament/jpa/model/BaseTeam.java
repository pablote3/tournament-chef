package com.rossotti.tournament.jpa.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("B")
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"teamName"}))
public class BaseTeam extends AvailableTeam {
}