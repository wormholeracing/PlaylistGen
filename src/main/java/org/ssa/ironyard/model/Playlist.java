package org.ssa.ironyard.model;

public class Playlist extends DomainObject {

    private final String name;
    private final User user;
    private final Integer targetDuration;

    public Playlist(Integer id, boolean loaded, String name, User user, Integer targetDuration) {
	super(id, loaded);
	this.name = name;
	this.user = user;
	this.targetDuration = targetDuration;
    }

    public Integer getTargetDuration() {
	return targetDuration;
    }

    public String getName() {
	return name;
    }

    public User getUser() {
	return user;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	DomainObject other = (DomainObject) obj;
	if (id == null) {
	    return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    @Override
    public boolean deeplyEquals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Playlist other = (Playlist) obj;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (targetDuration == null) {
	    if (other.targetDuration != null)
		return false;
	} else if (!targetDuration.equals(other.targetDuration))
	    return false;
	if (user == null) {
	    if (other.user != null)
		return false;
	} else if (!user.equals(other.user))
	    return false;
	return true;
    }

}