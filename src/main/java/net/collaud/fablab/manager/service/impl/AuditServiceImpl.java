package net.collaud.fablab.manager.service.impl;

import java.util.Date;
import java.util.List;
import net.collaud.fablab.manager.dao.AuditRepository;
import net.collaud.fablab.manager.data.AuditEO;
import net.collaud.fablab.manager.data.type.AuditObject;
import net.collaud.fablab.manager.exceptions.FablabException;
import net.collaud.fablab.manager.security.Roles;
import net.collaud.fablab.manager.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gaetan Collaud <gaetancollaud@gmail.com>
 */
@Service
@Transactional
public class AuditServiceImpl extends AbstractServiceImpl implements AuditService {

	@Autowired
	private AuditRepository auditDAO;

	@Override
	public AuditEO addEntry(AuditEO entry) throws FablabException {
		return auditDAO.save(entry);
	}

	@Override
	@Secured({Roles.AUDIT_VIEW})
	public List<AuditEO> search(Long userId, List<AuditObject> type, Date after, Date before, String content, int limit) throws FablabException {
		return auditDAO.search(userId, type, after, before, content, limit);
	}
}
