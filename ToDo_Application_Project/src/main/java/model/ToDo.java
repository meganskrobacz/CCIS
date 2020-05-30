package assignment9.model;

import java.time.LocalDate;
import java.util.Locale.Builder;
import java.util.Objects;

public class ToDo {

  private int id;
  private String description;
  private boolean completed;
  private Integer priority;
  private LocalDate dueDate;
  private String category;

  /**
   * A ToDo object
   * @param builder a ToDo builder
   */
  private ToDo(Builder builder) {
    this.id = builder.id;
    this.description = builder.description;
    this.completed = builder.completed;
    this.priority = builder.priority;
    this.dueDate = builder.dueDate;
    this.category = builder.category;
  }

  /**
   * Gets the ID of the ToDo
   * @return the ID of the ToDo
   */
  public int getID() {
    return this.id;
  }

  /**
   * Gets the text description of the ToDo
   * @return the text description of the ToDo
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Gets if the ToDo has been completed.
   * @return True if completed, false otherwise.
   */
  public boolean isCompleted() {
    return this.completed;
  }

  /**
   * Gets the priority of the ToDo.
   * @return 1, 2, or 3 priority, or null if no priority.
   */
  public Integer getPriority() {
    return this.priority;
  }

  /**
   * Gets the due date of the ToDo.
   * @return the due date of the ToDo, or null if no date.
   */
  public LocalDate getDueDate() {
    return this.dueDate;
  }

  /**
   * Gets the category of the ToDo.
   * @return the category of the ToDo, or null if no category.
   */
  public String getCategory() {
    return this.category;
  }

  /**
   * Changes the "completed" of the ToDo.
   * @param completed True if completed, false if not completed.
   */
  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  public static class Builder {
    private int id;
    private String description;
    private boolean completed;
    private Integer priority;
    private LocalDate dueDate;
    private String category;

    /**
     * The builder class for the ToDo object.
     */
    public Builder (int id, String description) {
      this.id = id;
      this.description = description;
      this.completed = false;
      this.priority = null;
      this.dueDate = null;
      this.category = null;
    }

    /**
     * Adds completed to the ToDo
     * @param completed true if completed, false if not completed.
     * @return the ToDo builder.
     */
    public Builder complete(boolean completed) {
      this.completed = completed;
      return this;
    }

    /**
     * Adds a due date to the ToDo
     * @param dueDate the due date
     * @return the ToDo builder.
     */
    public Builder addDueDate(LocalDate dueDate) {
      this.dueDate = dueDate;
      return this;
    }

    /**
     * Adds a priority to the ToDo
     * @param priority 1, 2, or 3 priority.
     * @return the ToDo builder.
     */
    public Builder addPriority(Integer priority) {
      this.priority = priority;
      return this;
    }

    /**
     * Adds a category to the ToDo
     * @param category any text category
     * @return the ToDo builder.
     */
    public Builder addCategory (String category) {
      this.category = category;
      return this;
    }

    /**
     * Builds the ToDo object.
     * @return a Todo object
     */
    public ToDo build() {return new ToDo(this);}
  }

  /**
   * Checks equality of a ToDo object.
   * @param o the object to check
   * @return true if the objects are the same, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ToDo toDo = (ToDo) o;
    return this.id == toDo.id &&
        this.completed == toDo.completed &&
        Objects.equals(this.description, toDo.description) &&
        Objects.equals(this.priority, toDo.priority) &&
        Objects.equals(this.dueDate, toDo.dueDate) &&
        Objects.equals(this.category, toDo.category);
  }

  /**
   * Generates a hash code for the ToDo object.
   * @return the hash code for the ToDo object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.description, this.completed, this.priority, this.dueDate,
        this.category);
  }

  /**
   * Generates a string representing the ToDo object.
   * @return a string representing the ToDo object.
   */
  @Override
  public String toString() {
    return "ToDo ID = " + this.id +
        ", description = " + this.description +
        ", completed = " + this.completed +
        ", priority = " + this.priority +
        ", dueDate = " + this.dueDate +
        ", category = " + this.category;
  }
}
