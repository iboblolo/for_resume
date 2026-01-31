namespace EXAM_ASP
{
    using System;
    using System.Threading.Tasks;
    using System.Security.Claims;
    using Microsoft.AspNetCore.Authorization;
    using Models;

    public class TeacherHandler : AuthorizationHandler<TeacherRequirement>
    {
        protected override Task HandleRequirementAsync(AuthorizationHandlerContext context,
            TeacherRequirement requirement)
        {
            var isT = context.User.FindFirst(c => c.Type == "isTeacher");
            if (isT is not null)
            {
                if (bool.TryParse(isT.Value, out var res))
                {
                    if(res)
                    {
                        context.Succeed(requirement);
                    }
                }
            }
            return Task.CompletedTask;
        }
    }
}
