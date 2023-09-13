package satify.update

object Utils:

  /** Chronometer to measure the time elapsed between two events. */
  object chronometer:
    private var t0: Long = 0
    private var t1: Long = 0

    def start(): Unit = t0 = System.nanoTime()
    def stop(): Unit = t1 = System.nanoTime()
    def elapsed(): Long = (t1 - t0) / 1000
