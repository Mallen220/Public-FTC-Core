// Title: SolversLib Timer
// Description: A timer that reports when it's done.
import com.seattlesolvers.solverslib.util.Timing;
import java.util.concurrent.TimeUnit;

Timing.Timer ${1:timer} = new Timing.Timer(${2:2}, TimeUnit.SECONDS);
${1:timer}.start();
boolean done = ${1:timer}.done();
