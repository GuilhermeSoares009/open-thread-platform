# Threat Model

## Threats and Mitigations
- **IDOR**: enforce policies and access checks for community, post, and comment access.
- **XSS**: sanitize output in Blade views, avoid unsafe HTML rendering.
- **CSRF**: use Laravel CSRF protection for web forms.
- **Brute force / abuse**: rate limiting on auth and write endpoints.
- **Spam**: per-user throttling and minimum account age for write actions.
- **Privilege escalation**: policy checks and explicit role checks in admin routes.
- **Data leakage**: return only allowed fields via API resources.
- **Injection**: always use Eloquent/Query Builder with bound params.
