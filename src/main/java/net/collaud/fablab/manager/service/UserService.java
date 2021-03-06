package net.collaud.fablab.manager.service;

import java.util.Optional;
import net.collaud.fablab.manager.audit.Audit;
import net.collaud.fablab.manager.data.UserEO;
import net.collaud.fablab.manager.data.type.AuditAction;
import net.collaud.fablab.manager.data.type.AuditObject;
import net.collaud.fablab.manager.data.type.ChangePasswordResult;
import net.collaud.fablab.manager.service.global.ReadWriteService;

/**
 *
 * @author Gaetan Collaud <gaetancollaud@gmail.com>
 */
public interface UserService extends ReadWriteService<UserEO>{

	Optional<UserEO> findByLogin(String login);
	
	@Audit(object = AuditObject.USER, action = AuditAction.SIGNUP)
	void signup(UserEO user, String recaptcha);
	
	void forgotPassword(String email, String recaptchaResponse);

	UserEO acceptPasswordChange(UserEO user);

	void updateMailingList();

	@Override
	@Audit(object = AuditObject.USER, action = AuditAction.DELETE)
	void remove(Long id);

	@Override
	@Audit(object = AuditObject.USER, action = AuditAction.SAVE)
	UserEO save(UserEO entity);

	Optional<UserEO> findByRFID(String rfid);

	ChangePasswordResult changePassword(String old, String newPass, String repeatPass);
}
