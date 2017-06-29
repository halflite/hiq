package net.halflite.hiq.logic;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.pac4j.oauth.profile.twitter.TwitterProfile;

import com.google.inject.persist.Transactional;

import net.halflite.hiq.entity.Account;
import net.halflite.hiq.repository.AccountRepository;

@Singleton
public class AuthenticateLogic {

	/** アカウント情報のリポジトリ */
	private final AccountRepository accountRepository;

	@Transactional
	public Account findAccountOrCreate(final TwitterProfile profile) {
		String principal = profile.getId();
		Account account = this.accountRepository.findByPrincipal(principal);
		return Optional.ofNullable(account).orElseGet(() -> this.createAccount(profile));
	}

	protected Account createAccount(TwitterProfile profile) {
		Account account = new Account(profile.getId(), profile.getDisplayName(), profile.getDescription());
		this.accountRepository.insert(account);
		return account;
	}

	@Inject
	public AuthenticateLogic(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

}
