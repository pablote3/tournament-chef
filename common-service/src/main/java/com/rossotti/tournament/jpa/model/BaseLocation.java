package com.rossotti.tournament.jpa.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("B")
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"locationName"}))
public class BaseLocation extends AvailableLocation {
}
