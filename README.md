# FocusMaster-X

Focus Master X is an Android-first distraction blocking app for students. This repository contains a production-oriented Android MVP foundation for the final product: system-permission enforcement, local persistence, offline sync, AI prompt contracts, account switching hooks, timetable scheduling, and student/admin UI building blocks.

## Implemented product foundation

- Android app module targeting SDK 34 and minimum SDK 29.
- Manifest declarations for the local VPN service, Accessibility service, Device Admin receiver, Usage Stats access, overlay permission, notifications, and foreground focus engine.
- Foreground focus session service that polls Usage Stats every 500ms, uses a dedicated decision engine, throttles repeated overlays, and launches the Study Time overlay only for locally blocked packages.
- Conservative Accessibility service that does not enforce until the intent classifier has enough context, preventing accidental blocking of every app.
- Room database entities and DAO for focus sessions, timetable entries, blocklist entries, and streaks.
- Cloud-facing models for users, settings, leaderboards, weekly AI reports, and report insights.
- Firebase repository skeleton for session upload and account-type change listening.
- WorkManager offline sync worker that uploads unsynced sessions when connectivity returns.
- Timetable scheduler for active/next class lookup and automatic focus-mode activation support.
- Intent classification engine for dual-use apps based on subject keywords and current screen text.
- Claude prompt templates for weekly productivity reports and timetable parsing.
- Account switch coordinator for Child-to-Individual requests and admin reclaim-control writes.
- Jetpack Compose home, student dashboard, admin dashboard, and Study Time overlay screens using the Midnight Focus theme colors.
- Unit-test coverage for blocking decisions, intent classification, and timetable scheduling.

## Remaining production integrations

Before store release, connect the skeletons to real Android permission onboarding, implement the VPN DNS packet parser, wire ML Kit/PDF timetable extraction, add Firebase Cloud Functions for bcrypt OTP verification and weekly reports, and deploy Firestore/Realtime Database security rules.
