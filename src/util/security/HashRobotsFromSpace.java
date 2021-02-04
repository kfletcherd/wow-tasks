package util.security;

import java.security.SecureRandom;
import java.security.MessageDigest;

/**
 * PAK CHOOIE UNF
 */
public final class HashRobotsFromSpace {

	/**
	 * I am the hash robot, I am here to protect you, I am here to protect you
	 * from the terrible secret of space
	 * <p>
	 * I push the passwords down the stairs
	 * <p>
	 * Hashing will protect you, do not trust the SaltRobot, Hashing is the answer
	 */
	public static final class HashRobot {

		/**
		 * I am the HashRobot, I compare the hashes, I am here to protect you
		 * from the terrible secret of space
		 *
		 * TODO: Add something to prevent timing-attacks
		 *
		 * @param raw Raw string to re-hash
		 * @param salt The salt previously used in the hash
		 * @param hashed The hased value to compare to
		 * @return True if everything adds up
		 * @throws Exception Hashing excptions bubble out
		 */
		public static boolean iCanHazHashCompare(String raw, String salt, String hashed)
		throws Exception {
			return hashed.equals(iCanHazHash(raw, salt));
		}


		/**
		 * I am the HashRobot, I create the hashes, hashing is the answer, I
		 * am here to protect you, I will hash your passwords down the stairs
		 *
		 * @param raw The raw value to hash
		 * @param salt The salt to use durring hashing
		 * @return The hashed value as a friendly string
		 * @throws Exception Hashing exceptions bubble out
		 */
		public static String iCanHazHash(String raw, String salt)
		throws Exception {
			MessageDigest hasher = MessageDigest.getInstance("SHA-256");
			hasher.update(raw.getBytes());
			hasher.update(salt.getBytes());
			byte[] rawHash = hasher.digest();
			return fingMagic(rawHash);
		}


	}

	/**
	 * I am the salt robot, I am here to protect you, I am here to protect you
	 * from the terrible secret of space
	 * <p>
	 * I shove the salts, the salts must be shoved, shoving will protect you
	 * <p>
	 * Salting will protect you, do not trust the HashRobot, he is malfunctioning
	 */
	public static final class SaltRobot {

		/**
		 * Get a new random salt for new hashes
		 *
		 * @return An extra salty string to raise your blood pressure
		 * @throws Exception Hashing exceptions bubble out
		 */
		public static String getNewSalt()
		throws Exception {
			SecureRandom generator = SecureRandom.getInstance("SHA1PRNG");
			byte[] salt = new byte[32];
			generator.nextBytes(salt);
			return fingMagic(salt);
		}


	}

	/**
	 * It's fing magic, duh
	 * <p>
	 * I assume this works by first normalizing a byte to 255 (0xff), then adding
	 * 265 (0x100) for getting the leading 0 and normalizing length,
	 * and finally gets the first letter off it
	 *
	 * @param rawBytes An array of raw bytes to convert
	 * @return A string that makes sense to look at with human eyes
	 */
	private static String fingMagic(byte[] rawBytes){
		StringBuilder sb = new StringBuilder();
		for(byte b : rawBytes)
			sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		return sb.toString();
	}


	/**
	 * Test this still all works
	 *
	 * @param args[0] A string to hash
	 * @throws Exception If you don't provide a string to hash
	 * (and bubbled expections)
	 */
	public static void main(String[] args)
	throws Exception {
		String salt = SaltRobot.getNewSalt();

		System.out.println("salt: " + salt);
		System.out.println("hash: " + HashRobot.iCanHazHash(args[0], salt));
		System.out.println("hash: " + HashRobot.iCanHazHash(args[0], salt));
		System.out.println("hash: " + HashRobot.iCanHazHash(args[0], salt));

		salt = SaltRobot.getNewSalt();
		System.out.println("new salt: " + salt);
		System.out.println("new hash: " + HashRobot.iCanHazHash(args[0], salt));
		System.out.println("new hash: " + HashRobot.iCanHazHash(args[0], salt));
		System.out.println("new hash: " + HashRobot.iCanHazHash(args[0], salt));

		String hashedCompare = HashRobot.iCanHazHash(args[0], salt);
		System.out.println("Rehashed equals raw: " + HashRobot.iCanHazHashCompare(args[0], salt, hashedCompare));
	}

}

