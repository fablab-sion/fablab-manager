package net.collaud.fablab.api.service.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import net.collaud.fablab.api.dao.AuditDAO;
import net.collaud.fablab.api.data.AuditEO;
import net.collaud.fablab.api.data.UserEO;
import net.collaud.fablab.api.data.type.AuditObject;
import net.collaud.fablab.api.exceptions.FablabException;
import net.collaud.fablab.api.security.RolesHelper;
import net.collaud.fablab.api.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gaetan
 */
@Service
@Transactional
@RolesAllowed({RolesHelper.ROLE_ADMIN})
public class AuditServiceImpl extends AbstractServiceImpl implements AuditService {

	@Autowired
	private AuditDAO auditDAO;

	@Override
	@PermitAll
	public AuditEO addEntry(AuditEO entry) throws FablabException {
		return auditDAO.addEntry(entry);
	}

	@Override
	@RolesAllowed({RolesHelper.ROLE_VIEW_AUDIT})
	public List<AuditEO> search(UserEO user, List<AuditObject> type, Date after, Date before, String content, int limit) throws FablabException {
		return auditDAO.search(user, type, after, before, content, limit);
	}
}