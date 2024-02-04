package eu.kubenetic;

import java.nio.charset.StandardCharsets;

public enum ClamDControl {
    /**
     * Check the server's state. It should reply with "PONG".
     */
    PING("PING"),

    /**
     * Print program and database versions.
     */
    VERSION("VERSION"),

    /**
     * Reload the virus databases.
     */
    RELOAD("RELOAD"),

    /**
     * Perform a clean exit.
     */
    SHUTDOWN("SHUTDOWN"),

    /**
     * Scan a file or a directory (recursively) with archive support enabled (if not disabled in clamd.conf).
     * A full path is required.
     */
    SCAN("SCAN"),

    RAWSCAN("RAWSCAN"),

    /**
     * Scan file or directory (recursively) with archive support enabled and don't stop the scanning when a
     * virus is found.
     */
    CONTSCAN("CONTSCAN"),

    /**
     * Scan file in a standard way or scan directory (recursively) using multiple threads (to make the scanning
     * faster on SMP machines).
     */
    MULTISCAN("MULTISCAN"),

    /**
     *
     */
    ALLMATCHSCAN("ALLMATCHSCAN"),

    /**
     * It is mandatory to prefix this command with n or z.
     *
     * Scan a stream of data. The stream is sent to clamd in chunks, after INSTREAM, on the same socket on which the
     * command was sent. This avoids the overhead of establishing new TCP connections and problems with NAT. The
     * format of the chunk is: '<length><data>' where <length> is the size of the following data in bytes expressed
     * as a 4 byte unsigned integer in network byte order and <data> is the actual chunk. Streaming is terminated by
     * sending a zero-length chunk. Note: do not exceed StreamMaxLength as defined in clamd.conf, otherwise clamd will
     * reply with INSTREAM size limit exceeded and close the connection.
     */
    INSTREAM("zINSTREAM\0"),

    /**
     * It is mandatory to newline terminate this command, or prefix with n or z.
     *
     * This command only works on UNIX domain sockets. Scan a file descriptor. After issuing a FILDES command a
     * subsequent rfc2292/bsd4.4 style packet (with at least one dummy character) is sent to clamd carrying the file
     * descriptor to be scanned inside the ancillary data. Alternatively the file descriptor may be sent in the same
     * packet, including the extra character.
     */
    FILDES("nFILDES"),

    /**
     * IIt is mandatory to newline terminate this command, or prefix with n or z, it is recommended to only use the z prefix.
     *
     * Replies with statistics about the scan queue, contents of scan queue, and memory usage. The exact reply format
     * is subject to change in future releases.
     */
    STATS("nSTATS"),

    /**
     * It is mandatory to prefix this command with n or z, and all commands inside IDSESSION must be prefixed.
     *
     * Start a clamd session. Within a session multiple SCAN, INSTREAM, FILDES, VERSION, STATS commands can be sent
     * on the same socket without opening new connections. Replies from clamd will be in the form '<id>: <response>'
     * where <id> is the request number (in ascii, starting from 1) and <response> is the usual clamd reply. The reply
     * lines have same delimiter as the corresponding command had. Clamd will process the commands asynchronously, and
     * reply as soon as it has finished processing.
     *
     * Clamd requires clients to read all the replies it sent, before sending more commands to prevent send() deadlocks.
     * The recommended way to implement a client that uses IDSESSION is with non-blocking sockets, and a select()/poll()
     * loop: whenever send would block, sleep in select/poll until either you can write more data, or read more replies.
     * Note that using non-blocking sockets without the select/poll loop and alternating recv()/send() doesn't comply
     * with clamd's requirements.
     *
     * If clamd detects that a client has deadlocked, it will close the connection. Note that clamd may close an
     * IDSESSION connection too if you don't follow the protocol's requirements. The client can use the PING
     * command to keep the connection alive.
     */
    IDSESSION("nIDSESSION"),

    /**
     * End a clamd session
     */
    END("END"),

    /**
     * Perform a clean exit
     */
    SIGTERM("SIGTERM"),

    /**
     * Reopen the log file
     */
    SIGHUP("SIGHUP"),

    /**
     * Reload the database
     */
    SIGUSR2("SIGUSR2");

    private final String command;

    private ClamDControl(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public byte[] getCommandAsBytes() {
        return command.getBytes(StandardCharsets.UTF_8);
    }
}
