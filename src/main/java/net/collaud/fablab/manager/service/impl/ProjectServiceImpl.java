package net.collaud.fablab.manager.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import net.collaud.fablab.manager.dao.MachineRepository;
import net.collaud.fablab.manager.dao.ProjectRepository;
import net.collaud.fablab.manager.data.MachineEO;
import net.collaud.fablab.manager.data.ProjectEO;
import net.collaud.fablab.manager.security.Roles;
import net.collaud.fablab.manager.service.MachineService;
import net.collaud.fablab.manager.service.ProjectService;
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
@Secured({Roles.ADMIN})
public class ProjectServiceImpl extends AbstractServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	//FIXME roles
	@Secured({Roles.RESERVATION_USE, Roles.PAYMENT_MANAGE, Roles.MACHINE_VIEW})
	public List<ProjectEO> findAll() {
		return projectRepository.findAll();
	}

	@Override
	@Secured({Roles.RESERVATION_USE, Roles.PAYMENT_MANAGE, Roles.MACHINE_VIEW})
	public Optional<ProjectEO> getById(Integer id) {
		return Optional.ofNullable(projectRepository.findOne(id));
	}
}
