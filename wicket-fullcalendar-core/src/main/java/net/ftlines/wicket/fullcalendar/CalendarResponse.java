/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.ftlines.wicket.fullcalendar;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.string.Strings;

public class CalendarResponse
{
	private final FullCalendar calendar;
	private final AjaxRequestTarget target;

	public CalendarResponse(FullCalendar calendar, AjaxRequestTarget target)
	{
		this.calendar = calendar;
		this.target = target;
	}

	public CalendarResponse refetchEvents()
	{
		return execute(q("refetchEvents"));
	}

	public CalendarResponse refetchEvents(EventSource source)
	{
		toggleEventSource(source, false);
		return toggleEventSource(source, true);
	}

	public CalendarResponse refetchEvent(EventSource source, Event event)
	{
		// for now we have an unoptimized implementation
		// later we can replace this by searching for the affected event in the clientside buffer
		// and refetching it

		return refetchEvents(source);
	}


	public CalendarResponse toggleEventSource(EventSource source, boolean enabled)
	{
		return execute(q("toggleSource"), q(source.getUuid()), String.valueOf(enabled));
	}

	public AjaxRequestTarget getTarget()
	{
		return target;
	}

	private CalendarResponse execute(String... args)
	{
		String js = String.format("$('#%s').fullCalendarExt(" + Strings.join(",", args) + ");", calendar.getMarkupId());
		target.appendJavascript(js);
		return this;
	}

	private static final String q(Object o)
	{
		if (o == null)
		{
			return "null";
		}
		else
		{
			return "'" + o.toString() + "'";
		}
	}


}
