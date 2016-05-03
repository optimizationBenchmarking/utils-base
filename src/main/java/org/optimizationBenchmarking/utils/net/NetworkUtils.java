package org.optimizationBenchmarking.utils.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;

import org.optimizationBenchmarking.utils.collections.iterators.EnumerationIterator;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * The local host class provides information about the local host on which
 * this software is running. It can give us some information about the
 * IP-address and name of the current computer and to enable to decide
 * whether an incoming connection comes from the local computer or from
 * another one.
 */
public final class NetworkUtils {

  /** the local host address */
  public static final String LOCAL_HOST = "localhost"; //$NON-NLS-1$

  /**
   * Obtain the set of internet addresses assigned to the local host. These
   * addresses are ordered such that the address which is most likely known
   * to the outside world comes first. If there are no other local
   * addresses than the loopback address, this method returns an empty
   * list.
   *
   * @return the set of internet addresses assigned to the local host.
   */
  public static final ArrayListView<InetAddress> getLocalAddresses() {
    return __LocalAddresses.LOCAL_ADDRESSES;
  }

  /**
   * Obtain the set of internet addresses of this host as seen from the
   * outside world. Each such address has two features:
   * <ol>
   * <li>At least computer outside the local network of the present
   * computer sees this address if we send a message to it.</li>
   * <li>If we send a (UDP) message to the address, it can be received by a
   * server running on this computer.</li>
   * </ol>
   * <p>
   * This is achieved as follows: we try to obtain the address under which
   * the current computer can be seen in the internet. We now do this in
   * two steps:
   * </p>
   * <ol>
   * <li>We query existing web pages that simply print the IP address from
   * which they receive the request. This way we get the address under
   * which outside computers see requests from the current computer.</li>
   * <li>We start a UDP server at a random port of the local computer. For
   * each address obtained under point 1, we send a message to the UDP
   * server. If it is received, we know that the address very likely
   * belongs to our computer is not NATed. Of course, we may be unlucky
   * with NAT and port forwards, but this is as good as we can get.</li>
   * </ol>
   *
   * @return the set of internet addresses under which this host is seen by
   *         the outside world.
   */
  public static final ArrayListView<InetAddress> getGlobalAddresses() {
    return __GlobalAddresses.GLOBAL_ADDRESSES;
  }

  /**
   * <p>
   * Get the most-likely public address of this computer, i.e., the address
   * which most-likely can be used to access server processes running on
   * this computer from the outside world. There is no guarantee that this
   * is actually the IP address which can be used for accessing the server.
   * </p>
   * <p>
   * In the ideal case, it would be an address which occurs in both
   * {@link #getGlobalAddresses()} and {@link #getLocalAddresses()}. But
   * since either of these lists may be empty, it may also just be the
   * local loop-back address.
   * </p>
   *
   * @return the most likely used public address of this server
   * @see #getMostLikelyPublicName()
   */
  public static final InetAddress getMostLikelyPublicAddress() {
    return __PublicAddress.PUBLIC_ADDRESS;
  }

  /**
   * Get the most-likely public name of this computer.
   *
   * @return the most-likely public name of this computer.
   * @see #getMostLikelyPublicAddress()
   */
  public static final String getMostLikelyPublicName() {
    return __PublicName.PUBLIC_NAME;
  }

  /**
   * Check whether this address belongs to the local computer.
   *
   * @param addr
   *          the address
   * @return {@code true} if this address belongs to one of the network
   *         interfaces of this computer, {@code false} otherwise
   */
  public static final boolean isLocalAddress(final InetAddress addr) {
    if (addr == null) {
      throw new IllegalArgumentException(
          "Cannot check if null address is local."); //$NON-NLS-1$
    }
    if (addr.isLoopbackAddress()) {
      return true;
    }
    return NetworkUtils.getLocalAddresses().contains(addr);
  }

  /**
   * Get a server name for the internet address
   *
   * @param addr
   *          the internet address
   * @return the server name for the internet address
   */
  @SuppressWarnings("unused")
  public static final String getInetAddressName(final InetAddress addr) {

    if (addr == null) {
      throw new IllegalArgumentException(//
          "Internet address cannot be null."); //$NON-NLS-1$
    }

    if (addr.isLoopbackAddress()) {
      return NetworkUtils.LOCAL_HOST;
    }

    try {
      return addr.getCanonicalHostName();
    } catch (final Throwable error) {
      return addr.getHostAddress();
    }
  }

  /**
   * Obtain the URL for a given host and port.
   *
   * @param host
   *          the host
   * @param port
   *          the port
   * @return the URL
   */
  @SuppressWarnings("unused")
  public static final URL getServerBaseURL(final String host,
      final int port) {
    final InetAddress addr;
    final String useHost;

    try {
      addr = InetAddress.getByName(host);
    } catch (final Throwable error) {
      throw new IllegalArgumentException("Host '" + host + //$NON-NLS-1$
          "' is invalid."); //$NON-NLS-1$
    }
    if (addr instanceof Inet6Address) {
      useHost = '[' + addr.getHostAddress() + ']';
    } else {
      useHost = host;
    }

    try {
      return new URL((("http://" + useHost) + ':' + port) + '/'); //$NON-NLS-1$
    } catch (final MalformedURLException error) {
      throw new IllegalArgumentException(((((((//
      "Error while creating URL for host '" + host) //$NON-NLS-1$
          + " (represented as '") + useHost + //$NON-NLS-1$
          "') and port ")//$NON-NLS-1$
          + port) + '\'') + '.'), error);
    }
  }

  /**
   * open a UDP socket at a random port.
   *
   * @return the socket
   * @throws IOException
   *           if the method fails after a reasonable amount of attempts
   */
  public static final DatagramSocket openUDPServerSocketAtRandomPort()
      throws IOException {
    final Random random;
    int maxTrials;

    random = new Random();
    maxTrials = 256;
    for (;;) {
      try {
        return new DatagramSocket(1024 + random.nextInt(64512));
      } catch (final IOException ioe) {
        if ((--maxTrials) < 0) {
          throw ioe;
        }
      }
    }
  }

  /** The holder class for the local addresses */
  private static final class __LocalAddresses {

    /** the local addresses */
    static final ArrayListView<InetAddress> LOCAL_ADDRESSES = __LocalAddresses
        .__createLocalAddresses();

    /**
     * Create the local addresses
     *
     * @return the local addresses
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static final ArrayListView<InetAddress> __createLocalAddresses() {
      final HashSet<InetAddress> addresses;
      final InetAddress[] addrs;
      final int size;
      InetAddress addr;

      // http://stackoverflow.com/questions/2939218/

      addresses = new HashSet<>();
      try {
        try {
          addr = InetAddress.getLocalHost();
          if ((addr != null) && (!(addr.isLoopbackAddress()))) {
            addresses.add(addr);
          }
        } catch (final Throwable lerror) {
          ErrorUtils.logError(Configuration.getGlobalLogger(),
              Level.WARNING, //
              "Recoverable/ignoreable error while obtaining the local host.", //$NON-NLS-1$
              lerror, false, RethrowMode.DONT_RETHROW);
        }

        for (final NetworkInterface netint : new EnumerationIterator<>(
            NetworkInterface.getNetworkInterfaces())) {
          try {
            for (final InetAddress inetAddress : new EnumerationIterator<>(
                netint.getInetAddresses())) {
              try {
                if (inetAddress == null) {
                  continue;
                }
                if (inetAddress.isLoopbackAddress()) {
                  continue;
                }
                if (inetAddress.isMulticastAddress()) {
                  continue;
                }

                addresses.add(inetAddress);

              } catch (final Throwable error) {
                ErrorUtils.logError(Configuration.getGlobalLogger(),
                    Level.WARNING, //
                    "Recoverable/ignoreable error while dealined with InetAddress "//$NON-NLS-1$
                        + inetAddress + " of network interfaces.", //$NON-NLS-1$
                    error, false, RethrowMode.DONT_RETHROW);
              }
            }
          } catch (final Throwable error) {
            ErrorUtils.logError(Configuration.getGlobalLogger(),
                Level.WARNING, //
                "Recoverable/ignoreable error while InetAddresses for network interfaces " //$NON-NLS-1$
                    + netint,
                error, false, RethrowMode.DONT_RETHROW);
          }
        }
      } catch (final Throwable error) {
        ErrorUtils.logError(Configuration.getGlobalLogger(), Level.WARNING, //
            "Recoverable/ignoreable error while obtaining network interfaces.", //$NON-NLS-1$
            error, false, RethrowMode.DONT_RETHROW);
      }

      size = addresses.size();
      if (size <= 0) {
        return ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
      }
      addrs = addresses.toArray(new InetAddress[size]);
      Arrays.sort(addrs, new __InetCmp());
      return new ArrayListView<>(addrs);
    }
  }

  /** The holder class for the global addresses */
  private static final class __GlobalAddresses {

    /** the global addresses */
    static final ArrayListView<InetAddress> GLOBAL_ADDRESSES = //
    __GlobalAddresses.__createGlobalAddresses();

    /**
     * Create the global addresses
     *
     * @return the global addresses
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static final ArrayListView<InetAddress> __createGlobalAddresses() {
      final String[] hosts;
      final __GlobalNameCollector collector;
      final __GlobalNameDetector[] threads;
      final int port;
      InetAddress[] addrs;
      int index;

      // http://superuser.com/questions/420969
      // http://stackoverflow.com/questions/2939218/
      hosts = new String[] { //
          "http://checkip.amazonaws.com/", //$NON-NLS-1$
          "http://icanhazip.com/", //$NON-NLS-1$
          // "http://curlmyip.com/", //$NON-NLS-1$ //seemingly doesn't work
          "http://curlmyip.de/", //$NON-NLS-1$
          "http://ipecho.net/plain", //$NON-NLS-1$
          "http://wtfismyip.com/text", //$NON-NLS-1$
      };

      addrs = null;
      try {
        collector = new __GlobalNameCollector();
        port = collector._getPort();

        threads = new __GlobalNameDetector[hosts.length];
        for (index = hosts.length; (--index) >= 0;) {
          threads[index] = new __GlobalNameDetector(hosts[index], port);
        }
        for (index = hosts.length; (--index) >= 0;) {
          threads[index].join();
          threads[index] = null;
        }

        collector._stop();
        collector.join();
        addrs = collector._getNames();
      } catch (final Throwable error) {
        ErrorUtils.logError(Configuration.getGlobalLogger(), Level.WARNING, //
            "Recoverable/ignoreable error while trying to obtain the internet addresses under which we are visible to the outside world.", //$NON-NLS-1$
            error, false, RethrowMode.DONT_RETHROW);
      }
      if (addrs != null) {
        if (addrs.length > 0) {
          return new ArrayListView<>(addrs);
        }
      }
      return ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
    }
  }

  /** The holder class for the public address */
  private static final class __PublicAddress {

    /** the public address */
    static final InetAddress PUBLIC_ADDRESS = __PublicAddress
        .__getPublicAddress();

    /**
     * Compute the public address
     *
     * @return the public address
     */
    private static final InetAddress __getPublicAddress() {
      ArrayListView<InetAddress> local, global;

      local = NetworkUtils.getLocalAddresses();
      global = NetworkUtils.getGlobalAddresses();

      for (final InetAddress addr : global) {
        if (local.contains(global)) {
          return addr;
        }
      }

      if (!(global.isEmpty())) {
        return global.get(0);
      }

      if (local.isEmpty()) {
        try {
          return InetAddress.getLocalHost();
        } catch (final Throwable lerror) {
          ErrorUtils.logError(Configuration.getGlobalLogger(),
              Level.WARNING, //
              "Recoverable/ignoreable error while obtaining the local host.", //$NON-NLS-1$
              lerror, false, RethrowMode.DONT_RETHROW);
        }
        return InetAddress.getLoopbackAddress();
      }

      return local.get(0);
    }
  }

  /** The holder class for the public name */
  private static final class __PublicName {
    /** the public name */
    static final String PUBLIC_NAME = NetworkUtils
        .getInetAddressName(__PublicAddress.PUBLIC_ADDRESS);
  }

  /** A comparator for internet addressess */
  private static final class __InetCmp implements Comparator<InetAddress> {

    /** create */
    __InetCmp() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final int compare(final InetAddress o1, final InetAddress o2) {
      return Integer.compare(__InetCmp._getVal(o1), __InetCmp._getVal(o2));
    }

    /**
     * get the value of an internet address for comparison
     *
     * @param a
     *          the address
     * @return the value
     */
    @SuppressWarnings("unused")
    static final int _getVal(final InetAddress a) {
      int val;
      val = 0;
      try {
        if (a == null) {
          return Integer.MAX_VALUE;
        }
        if (a.isLoopbackAddress()) {
          val |= 128;
        }
        if (a.isMulticastAddress()) {
          val |= 64;
        }
        if (a.isMCNodeLocal()) {
          val |= 32;
        }
        if (a.isMCLinkLocal()) {
          val |= 16;
        }
        if (a.isMCGlobal()) {
          val |= 8;
        }
        if (a.isSiteLocalAddress()) {
          val |= 4;
        }
        if (a.isLinkLocalAddress()) {
          val |= 2;
        }
        if (a.isAnyLocalAddress()) {
          val |= 1;
        }
        return val;
      } catch (final Throwable error) {
        return (Integer.MAX_VALUE - 1);
      }
    }
  }

  /** the local server thread. */
  private static final class __GlobalNameCollector extends Thread {
    /** the internal socket */
    private final DatagramSocket m_socket;
    /** the hosts */
    private HashMap<InetAddress, ___Holder> m_hosts;
    /** are we not done yet? */
    private volatile boolean m_alive;

    /**
     * create
     *
     * @throws IOException
     *           if the socket creation fails
     */
    __GlobalNameCollector() throws IOException {
      super();
      this.m_socket = NetworkUtils.openUDPServerSocketAtRandomPort();
      this.m_socket.setSoTimeout(2000);
      this.m_alive = true;
      this.start();
    }

    /** {@inheritDoc} */
    @Override
    public final void run() {
      DatagramPacket message;
      final byte[] buffer;
      byte[] temp;
      InetAddress addr;
      int length;
      ___Holder holder;

      try {
        buffer = new byte[16];
        message = new DatagramPacket(buffer, 0, buffer.length);

        looper: while (this.m_alive) {
          try {
            this.m_socket.receive(message);
            length = message.getLength();
            if (length > 0) {
              if (length != buffer.length) {
                temp = new byte[length];
                System.arraycopy(buffer, 0, temp, 0, length);
              } else {
                temp = buffer;
              }
              addr = InetAddress.getByAddress(temp);
              temp = null;

              if (this.m_hosts == null) {
                this.m_hosts = new HashMap<>();
              }
              holder = this.m_hosts.get(addr);
              if (holder == null) {
                holder = new ___Holder(addr);
                this.m_hosts.put(addr, holder);
              } else {
                holder.m_count++;
              }
            }
          } catch (@SuppressWarnings("unused") final SocketTimeoutException ste) {
            continue looper;
          } catch (final Throwable error) {
            ErrorUtils.logError(Configuration.getGlobalLogger(),
                Level.WARNING,
                "Ignorable error while trying to receive message from helper socket for network address determination.", //$NON-NLS-1$
                error, false, RethrowMode.DONT_RETHROW);
            break looper;
          }
        }
      } finally {
        try {
          this.m_socket.close();
        } catch (final Throwable error) {
          ErrorUtils.logError(Configuration.getGlobalLogger(),
              Level.WARNING,
              "Ignorable error while trying to close helper socket for network address determination.", //$NON-NLS-1$
              error, false, RethrowMode.DONT_RETHROW);
        }
      }
    }

    /** stop this server */
    final void _stop() {
      this.m_alive = false;
    }

    /**
     * get the port of this collector's server socket
     *
     * @return the port
     */
    final int _getPort() {
      return this.m_socket.getLocalPort();
    }

    /**
     * get this server's public names
     *
     * @return the collected names
     */
    final InetAddress[] _getNames() {
      final HashMap<InetAddress, ___Holder> holders;
      final ___Holder[] list;
      final InetAddress[] names;
      final int size;
      int index;

      this.m_alive = false;
      holders = this.m_hosts;
      this.m_hosts = null;

      if (holders != null) {
        size = holders.size();
        if (size > 0) {
          list = holders.values().toArray(new ___Holder[size]);
          Arrays.sort(list);
          names = new InetAddress[list.length];
          for (index = 0; index < names.length; index++) {
            names[index] = list[index].m_address;
          }
          return names;
        }
      }
      return null;
    }

    /** the holder */
    private static final class ___Holder implements Comparable<___Holder> {
      /** the name */
      final InetAddress m_address;
      /** the number of times it was returned */
      int m_count;

      /**
       * create the holder
       *
       * @param address
       *          the address
       */
      ___Holder(final InetAddress address) {
        super();
        this.m_address = address;
      }

      /** {@inheritDoc} */
      @Override
      public final int compareTo(final ___Holder o) {
        int comp;
        if (o == this) {
          return 0;
        }
        comp = Integer.compare(this.m_count, o.m_count);
        if (comp != 0) {
          return (-comp);
        }
        return Integer.compare(__InetCmp._getVal(this.m_address),
            __InetCmp._getVal(o.m_address));
      }
    }
  }

  /** the global name detector thread */
  private static final class __GlobalNameDetector extends Thread {
    /** the url to contact */
    private final String m_host;
    /** the destination port to send an udp package to */
    private final int m_destPort;

    /**
     * create the name detector thread
     *
     * @param url
     *          the url to contact
     * @param destPort
     *          the destination port to send the answer to
     */
    __GlobalNameDetector(final String url, final int destPort) {
      super();
      this.m_host = url;
      this.m_destPort = destPort;
      this.start();
    }

    /** {@inheritDoc} */
    @Override
    public final void run() {
      final URL url;
      final URLConnection connect;
      final InetAddress addr;
      String ip, z;
      int index;
      byte[] address;

      try {
        url = new URL(this.m_host);

        ip = null;

        connect = url.openConnection();
        connect.setDoOutput(false);
        connect.setAllowUserInteraction(false);
        connect.setDoInput(true);
        connect.setUseCaches(false);
        connect.setConnectTimeout(20000);
        connect.setDefaultUseCaches(false);
        connect.setReadTimeout(20000);
        connect.connect();
        try {
          try (final InputStream is = connect.getInputStream()) {
            try (final InputStreamReader isr = new InputStreamReader(is)) {
              try (final BufferedReader br = new BufferedReader(isr)) {
                ip = TextUtils.prepare(br.readLine());
              }
            }
          }
        } finally {
          if (connect instanceof HttpURLConnection) {
            ((HttpURLConnection) connect).disconnect();
          }
        }

        if (ip != null) {
          index = ip.lastIndexOf(' ');
          if (index > 0) {
            z = TextUtils.prepare(ip.substring(index + 1));
            if (z != null) {
              ip = z;
            }
          }

          try {
            addr = InetAddress.getByName(ip);
          } catch (final Throwable parse) {
            ErrorUtils.logError(Configuration.getGlobalLogger(),
                Level.WARNING, //
                ((("Recoverable/ignoreable error while trying to convert the internet address '"//$NON-NLS-1$
                    + ip + //
                    "' obtained from the website '" + //$NON-NLS-1$
                    this.m_host) + '\'') + '.'),
                parse, false, RethrowMode.DONT_RETHROW);
            return;
          }

          if (addr != null) {
            address = addr.getAddress();
            if (address != null) {
              try (DatagramSocket socket = new DatagramSocket()) {
                socket.send(new DatagramPacket(address, 0, address.length,
                    addr, this.m_destPort));
              }
            }
          }
        }
      } catch (final Throwable inner) {
        ErrorUtils.logError(Configuration.getGlobalLogger(), Level.WARNING, //
            ((("Recoverable/ignoreable error while trying to obtain the internet address by using the website '" //$NON-NLS-1$
                + this.m_host) + '\'') + '.'),
            inner, false, RethrowMode.DONT_RETHROW);
      }
    }
  }
}
