
package org.ssa.ironyard.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;
import org.ssa.ironyard.dao.orm.EpisodeORM;
import org.ssa.ironyard.dao.orm.ORM;
import org.ssa.ironyard.model.Episode;

@Component
public class EpisodeDAOImpl extends AbstractSpringDAO<Episode> implements EpisodeDAO {

    Logger LOGGER = LogManager.getLogger(EpisodeDAOImpl.class);
    
    @Autowired
    public EpisodeDAOImpl(DataSource dataSource) {
	this(new EpisodeORM() {
	}, dataSource);
    }

    protected EpisodeDAOImpl(ORM<Episode> orm, DataSource dataSource) {
	super(orm, dataSource);
    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, Episode domainToInsert) throws SQLException {
	insertStatement.setInt(1, domainToInsert.getEpisodeId());
	insertStatement.setString(2, domainToInsert.getName());
	insertStatement.setInt(3, domainToInsert.getDuration());
	insertStatement.setString(4, domainToInsert.getFileUrl());
	insertStatement.setString(5, domainToInsert.getShow().getName());
	insertStatement.setString(6, domainToInsert.getShow().getThumbUrl());
    }

    @Override
    protected Episode afterInsert(Episode copy, Integer id) {
	return Episode.builder(copy).id(id).loaded(true).build();
    }

    @Override
    protected Episode afterUpdate(Episode copy) {
	return Episode.builder(copy).loaded(true).build();
    }

    @Override
    protected PreparedStatementSetter updatePreparer(Episode domainToUpdate) {
	return new PreparedStatementSetter() {

	    @Override
	    public void setValues(PreparedStatement ps) throws SQLException {
		ps.setInt(1, domainToUpdate.getEpisodeId());
		ps.setString(2, domainToUpdate.getName());
		ps.setInt(3, domainToUpdate.getDuration());
		ps.setString(4, domainToUpdate.getFileUrl());
		ps.setString(5, domainToUpdate.getShow().getName());
		ps.setString(6, domainToUpdate.getShow().getThumbUrl());
		ps.setInt(7, domainToUpdate.getId());
	    }
	};
    }
    
    /* (non-Javadoc)
     * @see org.ssa.ironyard.dao.EpisodeDAO#insertIfNotExist(org.ssa.ironyard.model.Episode)
     */
    
    public Episode readByEpisodeId(Integer episodeId){
	return this.springTemplate.query(("SELECT " + this.orm.projection() + " FROM " + this.orm.table() + " WHERE episodeId=?"),
		(PreparedStatement ps) -> ps.setInt(1, episodeId), (ResultSet cursor) -> {
		    if(cursor.next())
			return this.orm.map(cursor);
		    return null;
		});
    }
    
    @Override
    public Episode insertIfAbsent(Episode episode){
	Episode existingEpisode = readByEpisodeId(episode.getEpisodeId());
	LOGGER.debug(existingEpisode);
	if(existingEpisode==null)
	    return insert(episode);
	LOGGER.debug("Update returned: {}", update(existingEpisode));
	return update(existingEpisode);
    }

}
