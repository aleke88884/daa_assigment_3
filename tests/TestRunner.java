package tests;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.engine.discovery.ClassNameFilter;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;

/**
 * Test runner for MST algorithm tests
 * Executes all JUnit tests and generates a summary
 */
public class TestRunner {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  MST ALGORITHMS TEST SUITE");
        System.out.println("========================================\n");

        // Build discovery request
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(MSTAlgorithmsTest.class))
                .build();

        // Create launcher and register listener
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);

        // Execute tests
        launcher.execute(request);

        // Get and print summary
        TestExecutionSummary summary = listener.getSummary();

        System.out.println("\n========================================");
        System.out.println("       TEST EXECUTION SUMMARY");
        System.out.println("========================================");
        System.out.printf("Tests found:      %d%n", summary.getTestsFoundCount());
        System.out.printf("Tests started:    %d%n", summary.getTestsStartedCount());
        System.out.printf("Tests successful: %d ✅%n", summary.getTestsSucceededCount());
        System.out.printf("Tests failed:     %d ❌%n", summary.getTestsFailedCount());
        System.out.printf("Tests skipped:    %d ⏭%n", summary.getTestsSkippedCount());
        System.out.printf("Tests aborted:    %d ⚠%n", summary.getTestsAbortedCount());
        System.out.println("========================================");

        // Print detailed failure information
        if (summary.getTestsFailedCount() > 0) {
            System.out.println("\n❌ FAILED TESTS:");
            summary.getFailures().forEach(failure -> {
                System.out.println("\n  Test: " + failure.getTestIdentifier().getDisplayName());
                System.out.println("  Reason: " + failure.getException().getMessage());
                System.out.println("  Exception: " + failure.getException().getClass().getSimpleName());
            });
            System.out.println("\n========================================");
        }

        // Print success message or exit with error
        if (summary.getTestsFailedCount() == 0) {
            System.out.println("\n✅ ALL TESTS PASSED SUCCESSFULLY!");
            System.out.println("========================================\n");
            System.exit(0);
        } else {
            System.out.println("\n❌ SOME TESTS FAILED - PLEASE FIX");
            System.out.println("========================================\n");
            System.exit(1);
        }
    }
}