namespace EXAM_ASP
{
    using Microsoft.AspNetCore.Authorization;

    public class TeacherRequirement : IAuthorizationRequirement
    {
        protected internal bool isTeacher { get; set; }

        public TeacherRequirement(bool _isTeacher)
        {
            isTeacher = _isTeacher;
        }
    }
}
