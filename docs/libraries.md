# Libraries

**FTC: Manage Libraries** adds, updates and removes FTC libraries so you don't have to edit Gradle files
by hand.

| Library | Versions | Sloth |
| --- | --- | --- |
| Sloth | 0.3.2, 0.2.4 | |
| FTC Dashboard | 0.6.0, 0.5.1, 0.4.17 | use the Sloth build |
| FTC Dashboard for Sloth | 0.3.2+0.6.0, 0.2.4+0.5.1 | requires Sloth |
| Panels | 1.0.18 (FTC SDK 12.0+) | works |
| Pedro Pathing | 3.0.1 | |
| Ivy | 1.1.1 | |
| TurtleTracerLib | 3.0.3, 3.0.0 | |
| Road Runner | 1.0.1 | works |
| NextFTC | 1.1.0, 1.0.1 | |
| SolversLib | 0.3.6, 0.3.5 | |
| goBILDA Pinpoint | built into FTC SDK 10.3+ | works |

Each library has a manifest in [`catalog/manifests`](../catalog/manifests). To add or update one, see
[CONTRIBUTING.md](../CONTRIBUTING.md). FTC Core downloads the catalog from GitHub once a day and checks
each library's Maven repository for newer releases, so new versions show up without a change here.

## Adding, updating and removing

Pick a library and FTC Core checks it against your FTC SDK, Sloth and other libraries. Anything it needs
comes along (Road Runner brings FTC Dashboard, for example). You see a diff of every Gradle file before
anything is written. If the sync fails afterwards, FTC Core offers to restore the old files. It keeps the
last 20 sets of backups, and **FTC: Show Extension Log** says where.

Edits follow the file's existing style. If a version lives in a variable or version catalog, FTC Core
shows you the change to make yourself.

Removing a library takes out its dependencies. Repositories stay, since other libraries may use them.
Dependencies that aren't in the catalog are left alone.

## Known problems

The catalog records library versions with bugs that only show up on the robot. When your project uses
one, FTC Core marks it in the Problems panel with a quick fix and asks before deploying it.
**FTC: Show Known Library Problems** lists them, and **FTC: Fix Known Library Problem** switches to the
fixed version.
