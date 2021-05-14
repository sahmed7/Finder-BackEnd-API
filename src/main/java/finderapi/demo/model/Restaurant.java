package finderapi.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String category;

    /********** add user **********/
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    /********** end of add user **********/

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Restaurant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }


    /********** user getters and setters **********/
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    /********** user getters and setters **********/
}
