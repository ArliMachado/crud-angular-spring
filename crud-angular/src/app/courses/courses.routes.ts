import { Routes } from '@angular/router';

import { CourseResolver } from './guards/course.resolver';
import { CoursesComponent } from './conteiners/courses/courses.component';
import { CoursesFormComponent } from './conteiners/courses-form/courses-form.component';

export const COURSES_ROUTES: Routes = [
  { path: '', component: CoursesComponent },
  { path: 'new', component: CoursesFormComponent, resolve: { course: CourseResolver } },
  { path: 'edit/:id', component: CoursesFormComponent, resolve: { course: CourseResolver } }
];
