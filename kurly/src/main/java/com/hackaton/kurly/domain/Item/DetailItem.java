package com.hackaton.kurly.domain.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
public class DetailItem {

    @Id
    private Integer id;

    private String name;

    private String key1; //Plus 3

    private String key2; //plus 2

    private String key3; //

    private String key4;

    private Integer minimumScore;

    public Item toItem(){
       return new Item(this.id, this.name);
    }
}



