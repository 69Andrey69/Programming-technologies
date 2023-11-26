package org.example;

@Table(Title = "Chicken")
class Chicken
{
    @Column(priority = 2)
    String name;
    @Column(priority = 3)
    int heightflyight;
    @Column (priority = 1)
    SizeKluv sizeKluv;
    public Chicken(String name, int heightflyight, SizeKluv sizeKluv)
    {
        this.name = name;
        this.heightflyight = heightflyight;
        this.sizeKluv = sizeKluv;
    }
}
