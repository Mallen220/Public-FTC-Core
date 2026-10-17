# Updating the FTC SDK

**FTC: Update FTC SDK** moves your project to a newer
[FtcRobotController](https://github.com/FIRST-Tech-Challenge/FtcRobotController) release and keeps your
code and your own build file changes.

It works like a git merge, comparing the release you're on and the one you're moving to with your project:

- files only *FIRST* changed get the new version,
- files only you changed keep yours,
- files you both changed are merged, with conflict markers where the same lines changed.

`TeamCode/src` is never touched. You see the plan first, including breaking changes and libraries that
may need updating. If the project has uncommitted git changes, FTC Core suggests committing first so the
update is one clean diff. Changed files are backed up.

Your project's current release has to be one FTC Core knows (9.2 through 12.0), since it's the base of
the merge.

## Afterwards

Run **FTC: Set Up Environment** in case the new release needs newer tools, update your libraries,
prepare for offline use again, and do a **Full Install** on each robot.

## By hand

For a release FTC Core doesn't recognize, merge it with git:

```sh
git remote add upstream https://github.com/FIRST-Tech-Challenge/FtcRobotController.git
git fetch upstream --tags
git merge v12.0
```
