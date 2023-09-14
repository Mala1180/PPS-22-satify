package satify.update

object Utils:

  /** Chronometer to measure the time elapsed between two events. */
  case object Chronometer:
    private var t0: Long = 0
    private var t1: Long = 0

    def start(): Unit = t0 = System.currentTimeMillis()
    def stop(): Unit = t1 = System.currentTimeMillis()
    def elapsed(): Long = t1 - t0
