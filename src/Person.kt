class Person(val name: String) {
    var children: MutableList<Person> = mutableListOf<Person>()
    constructor(name: String, parent: Person) : this(name) {
        parent.children.add(this)
    }
}